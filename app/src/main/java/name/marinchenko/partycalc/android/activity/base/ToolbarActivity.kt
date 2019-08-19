package name.marinchenko.partycalc.android.activity.base

import android.support.annotation.IdRes
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import name.marinchenko.partycalc.R
import org.jetbrains.anko.toast


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
            toast("Not implemented")
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}