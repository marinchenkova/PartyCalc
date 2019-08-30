package name.marinchenko.partycalc.android.activity

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import name.marinchenko.partycalc.R
import name.marinchenko.partycalc.android.activity.base.ToolbarActivity

class SettingsActivity : ToolbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        initToolbar(getString(R.string.title_activity_settings))

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
    }


    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}