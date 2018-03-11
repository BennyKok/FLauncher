package com.openlauncher.flauncher

import android.app.Application
import android.preference.PreferenceManager
import com.ftinc.scoop.Scoop

class LauncherApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Scoop.waffleCone()
                .addFlavor("Default", R.style.Theme_Scoop, true)
                .addFlavor("Light", R.style.Theme_Scoop_Light)
                .addDayNightFlavor("DayNight", R.style.Theme_Scoop_DayNight)
                .addFlavor("Alternate 1", R.style.Theme_Scoop_Alt1)
                .setSharedPreferences(PreferenceManager.getDefaultSharedPreferences(this))
                .initialize()
    }
}