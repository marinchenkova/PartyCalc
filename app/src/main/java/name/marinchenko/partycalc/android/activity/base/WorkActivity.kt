package name.marinchenko.partycalc.android.activity.base

import android.graphics.Rect
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.storage.session.SessionRepo
import org.jetbrains.anko.inputMethodManager

abstract class WorkActivity: ToolbarActivity() {

    protected abstract val baseLayout: View
    protected abstract val sessionRepo: SessionRepo


    protected fun showUndoSnackBar(@StringRes what: Int, action: () -> Unit) {
        Snackbar.make(base_layout, what, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo) { action() }
                .show()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_ENTER
                && event.action == KeyEvent.ACTION_UP) {
            val v = currentFocus
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
            baseLayout.requestFocus()
        }

        return super.dispatchKeyEvent(event)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    baseLayout.requestFocus()
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }

        return super.dispatchTouchEvent(event)
    }

}