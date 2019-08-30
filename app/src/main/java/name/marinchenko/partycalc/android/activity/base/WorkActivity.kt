package name.marinchenko.partycalc.android.activity.base

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.InstructionActivity
import name.marinchenko.partycalc.android.activity.SettingsActivity
import name.marinchenko.partycalc.android.storage.session.SessionRepo
import org.jetbrains.anko.startActivity

abstract class WorkActivity: InputActivity() {

    protected abstract val sessionRepo: SessionRepo


    protected fun showUndoSnackBar(@StringRes what: Int, action: () -> Unit) {
        Snackbar.make(base_layout, what, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo) { action() }
                .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu_settings, menu)
        menuInflater.inflate(R.menu.toolbar_menu_instruction, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.toolbar_popup_settings -> {
            startActivity<SettingsActivity>()
            true
        }
        R.id.toolbar_popup_instruction -> {
            startActivity<InstructionActivity>()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}