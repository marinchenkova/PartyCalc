package name.marinchenko.partycalc.android.activity

import android.graphics.Rect
import android.support.annotation.IdRes
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.EditText
import name.marinchenko.partycalc.R
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.toast


abstract class ToolbarActivity: AppCompatActivity() {

    protected abstract val baseLayout: View

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    open fun initToolbar(title: String? = null,
                         up: Boolean = true,
                         @IdRes toolbarId: Int = R.id.toolbar) {
        setSupportActionBar(findViewById(toolbarId))
        supportActionBar?.apply {
            this.title = title
            setDisplayHomeAsUpEnabled(up)
            setDisplayShowHomeEnabled(up)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        NavUtils.navigateUpFromSameTask(this)
        return true
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.toolbar_popup_settings -> {
            toast("Not implemented")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}