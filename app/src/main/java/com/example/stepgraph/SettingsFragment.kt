package com.example.stepgraph

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

/*
Settings needed:
- X Step goal
- X Data polling period (default 10 minutes)
- X Todays step graph, cumulative or steps/period (default cumulative)
- X Step distance (default 70cm)
- X Light/dark theme (default dark)
- X Persistent notification (default on)

 */

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}