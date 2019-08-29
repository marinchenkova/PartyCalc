package name.marinchenko.partycalc.android.activity.base

import android.graphics.Rect
import android.view.*
import androidx.annotation.IdRes
import androidx.core.app.NavUtils
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.SettingsActivity
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.startActivity
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
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.toolbar_popup_settings -> {
            startActivity<SettingsActivity>()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_ENTER
                && event.action == KeyEvent.ACTION_UP) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
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