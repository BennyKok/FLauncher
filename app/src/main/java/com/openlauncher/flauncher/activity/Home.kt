package com.openlauncher.flauncher.activity

import android.databinding.ObservableArrayList
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.afollestad.aesthetic.Aesthetic
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.openlauncher.fcore.Tool
import com.openlauncher.fcore.manager.app.AppManager
import com.openlauncher.fcore.manager.app.AppUtil.OnAppListChangedCallback
import com.openlauncher.fcore.model.data.App
import com.openlauncher.flauncher.R
import com.openlauncher.flauncher.model.item.AppItem
import com.openlauncher.flauncher.model.item.SectionGroupItem
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Aesthetic.attach(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        if (Aesthetic.isFirstTime()) {
            Aesthetic.get()
                    .colorPrimaryRes(R.color.colorPrimary)
                    .colorAccentRes(R.color.colorAccent)
                    .apply()
        }

        initTemplateUI()
        //initSimpleAppList()

        AppManager.load(packageManager)
    }

    private fun initTemplateUI() {
        allAppsList.layoutManager = LinearLayoutManager(this)
        val fastItemAdapter = FastItemAdapter<SectionGroupItem>()
        allAppsList.adapter = fastItemAdapter

        //For instance run to display the correctly
        if (AppManager.allApps.size != 0){
            val list = ArrayList<App>()
            (0..5).mapTo(list) { AppManager.allApps[it] }
            fastItemAdapter.clear()
            fastItemAdapter.add(SectionGroupItem("New", list))
            fastItemAdapter.add(SectionGroupItem("Recent", list))
        }
        AppManager.addChangedListener(object : AppManager.OnAppListChangedCallback() {
            override fun onAppListChanged(apps: ObservableArrayList<App>?) {
                Tool.print("Apps result received")

                val list = ArrayList<App>()
                (0..5).mapTo(list) { apps!![it] }
                fastItemAdapter.clear()
                fastItemAdapter.add(SectionGroupItem("New", list))
                fastItemAdapter.add(SectionGroupItem("Recent", list))
            }
        })
    }

    private fun initSimpleAppList() {
        allAppsList.layoutManager = GridLayoutManager(this, 4)
        val fastItemAdapter = FastItemAdapter<AppItem>()
        allAppsList.adapter = fastItemAdapter

        AppManager.addChangedListener(object : OnAppListChangedCallback() {
            override fun onAppListChanged(apps: ObservableArrayList<App>?) {
                Tool.print("Apps result received")

                fastItemAdapter.set(apps!!.map { AppItem(it) })
                fastItemAdapter.withOnClickListener { _, _, item, _ ->
                    item.app.start(this@Home)
                    true
                }
            }
        })
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
