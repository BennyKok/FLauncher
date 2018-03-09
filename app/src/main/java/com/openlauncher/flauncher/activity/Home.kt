package com.openlauncher.flauncher.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.openlauncher.fcore.manager.app.AppManager
import com.openlauncher.flauncher.R

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppManager.init(packageManager)
        setContentView(R.layout.activity_home)
    }
}
