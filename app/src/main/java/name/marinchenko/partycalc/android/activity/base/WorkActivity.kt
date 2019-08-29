package name.marinchenko.partycalc.android.activity.base

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.storage.session.SessionRepo

abstract class WorkActivity: ToolbarActivity() {

    protected abstract val sessionRepo: SessionRepo


    protected fun showUndoSnackBar(@StringRes what: Int, action: () -> Unit) {
        Snackbar.make(base_layout, what, Snackbar.LENGTH_SHORT)
                .setAction(R.string.undo) { action() }
                .show()
    }

}