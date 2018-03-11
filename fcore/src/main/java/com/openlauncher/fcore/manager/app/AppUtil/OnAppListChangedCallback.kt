package com.openlauncher.fcore.manager.app.AppUtil

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.openlauncher.fcore.Tool
import com.openlauncher.fcore.model.data.App

/**
 * Created by MichaelLam on 11/3/2018.
 */
abstract class OnAppListChangedCallback : ObservableList.OnListChangedCallback<ObservableArrayList<App>>() {

    abstract fun onAppListChanged(apps: ObservableArrayList<App>?)

    override fun onChanged(p0: ObservableArrayList<App>?) {
        Tool.print("1")
    }

    override fun onItemRangeChanged(p0: ObservableArrayList<App>?, p1: Int, p2: Int) {
        Tool.print("2")
    }

    override fun onItemRangeInserted(p0: ObservableArrayList<App>?, p1: Int, p2: Int) {
        Tool.print("3")
        onAppListChanged(p0)
    }

    override fun onItemRangeMoved(p0: ObservableArrayList<App>?, p1: Int, p2: Int, p3: Int) {
        Tool.print("4")
    }

    override fun onItemRangeRemoved(p0: ObservableArrayList<App>?, p1: Int, p2: Int) {
        Tool.print("5")
    }
}