package com.openlauncher.flauncher.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.afollestad.aesthetic.Aesthetic
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.openlauncher.fcore.manager.app.AppManager
import com.openlauncher.flauncher.R
import com.openlauncher.flauncher.model.item.AppItem
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Aesthetic.attach(this)
        super.onCreate(savedInstanceState)
        AppManager.init(packageManager)
        setContentView(R.layout.activity_home)

        if (Aesthetic.isFirstTime()) {
            Aesthetic.get()
                    .colorPrimaryRes(R.color.colorPrimary)
                    .colorAccentRes(R.color.colorAccent)
                    .apply()
        }


        initSimpleAppList()
    }

    private fun initSimpleAppList() {
        allAppsList.layoutManager = GridLayoutManager(this, 4)
        val fastItemAdapter = FastItemAdapter<AppItem>()
        allAppsList.adapter = fastItemAdapter

        AppManager.getAllApps().subscribe { allApps ->
            fastItemAdapter.set(allApps.map { AppItem(it) })
            fastItemAdapter.withOnClickListener { _, _, item, _ ->
                item.app.start(this)
                true
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Aesthetic.resume(this)
    }

    override fun onPause() {
        Aesthetic.pause(this)
        super.onPause()
    }


}
