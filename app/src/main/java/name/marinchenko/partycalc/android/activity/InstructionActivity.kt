package name.marinchenko.partycalc.android.activity

import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_instruction.*
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.base.ToolbarActivity
import name.marinchenko.partycalc.android.storage.getEasterActivated
import name.marinchenko.partycalc.android.util.setVisible
import org.jetbrains.anko.startActivity

class InstructionActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction)
        initToolbar(getString(R.string.instruction_title))
        initEaster()
    }

    private fun initEaster() {
        if (getEasterActivated()) easter.setVisible(true)
    }

}
