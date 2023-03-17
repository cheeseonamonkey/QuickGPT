package com.example.quickgpt.ui.dialogs.preferences

import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.quickgpt.R
import com.example.quickgpt.classes.PrefKey
import com.example.quickgpt.classes.PrefWriter
import com.example.quickgpt.classes.prefWriter

class PrefsPreferenceFragment : PreferenceFragmentCompat() , Preference.OnPreferenceChangeListener{




    val prefWriter : PrefWriter by lazy { requireContext().prefWriter }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.prefs_root, rootKey)

        val setApiKeyPreference = findPreference<Preference>("prf_set_api_key")!!

        setApiKeyPreference.onPreferenceChangeListener = this;

    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {

       var logstr = " \n'${preference?.key}' changed to:\n"

        when(preference?.key) {
            "prf_set_api_key" -> {

                prefWriter[PrefKey.API_KEY] = newValue!!.toString()

                logstr += "   " + prefWriter[PrefKey.API_KEY]
            }
            "prf_get_api_key" -> {

            }
            "prf_github" -> {

            }

        }

        Log.d("prefs", logstr)
        return true;
    }


}