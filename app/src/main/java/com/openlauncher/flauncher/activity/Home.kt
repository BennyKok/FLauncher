package com.openlauncher.flauncher.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ftinc.scoop.Scoop
import com.ftinc.scoop.ui.ScoopSettingsActivity
import com.openlauncher.fcore.manager.app.AppManager
import com.openlauncher.flauncher.R

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Scoop.getInstance().apply(this)
        AppManager.init(packageManager)
        setContentView(R.layout.activity_home)

        startActivityForResult(ScoopSettingsActivity.createIntent(this, "Change Themes"),46)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == 46){
                val restart = Intent(this, Home::class.java)
                finish()
                startActivity(restart)
                overridePendingTransition(0, 0)
            }
        }
    }
}
