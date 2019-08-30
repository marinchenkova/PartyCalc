package name.marinchenko.partycalc.android.activity.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import name.marinchenko.partycalc.R


abstract class ToolbarActivity: AppCompatActivity() {

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

}