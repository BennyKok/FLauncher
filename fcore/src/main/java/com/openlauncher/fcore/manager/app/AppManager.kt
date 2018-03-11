package com.openlauncher.fcore.manager.app

import android.content.Intent
import android.content.pm.PackageManager
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.openlauncher.fcore.Tool
import com.openlauncher.fcore.manager.app.AppUtil.OnAppListChangedCallback
import com.openlauncher.fcore.model.data.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.text.Collator
import kotlin.collections.ArrayList

object AppManager {

    private var packageManager: PackageManager? = null
    private var allApps: ObservableArrayList<App> = ObservableArrayList()
    private var subscription: Disposable? = null
//    private val loader: Observable<ArrayList<App>> = Observable.fromCallable {
//        Tool.print("Reli Loading Apps")
//        val apps = ArrayList<App>()
//
//        val intent = Intent(Intent.ACTION_MAIN, null)
//        intent.addCategory(Intent.CATEGORY_LAUNCHER)
//        val activitiesInfo = packageManager!!.queryIntentActivities(intent, 0)
//        activitiesInfo.sortWith(Comparator { p1, p2 -> Collator.getInstance().compare(p1.loadLabel(packageManager).toString(), p2.loadLabel(packageManager).toString()) })
//
//        activitiesInfo
//                .map { App(packageManager!!, it) }
//                .forEach { apps.add(it) }
//
//        return@fromCallable apps
//    }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())

    private fun getAllApps(): Observable<ArrayList<App>> = Observable.fromCallable {
        Tool.print("Reli Loading Apps")
        val apps = ArrayList<App>()

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val activitiesInfo = packageManager!!.queryIntentActivities(intent, 0)
        activitiesInfo.sortWith(Comparator { p1, p2 -> Collator.getInstance().compare(p1.loadLabel(packageManager).toString(), p2.loadLabel(packageManager).toString()) })

        activitiesInfo
                .map { App(packageManager!!, it) }
                .forEach { apps.add(it) }

        return@fromCallable apps
    }

    fun load(packageManager: PackageManager) {
        Tool.print("Start loading Apps")
        AppManager.packageManager = packageManager

        if (subscription != null) subscription!!.dispose()

        subscription = getAllApps()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    allApps.clear()
                    allApps.addAll(it)

                    subscription?.dispose()
                }, {
                    Tool.print("Some error: ${it.message}")

                    subscription?.dispose()
                })
    }

    fun addChangedListener(callback: OnAppListChangedCallback) {
        allApps.addOnListChangedCallback(callback)
    }

    fun removeChangedListener(callback: OnAppListChangedCallback) {
        allApps.removeOnListChangedCallback(callback)
    }

//    abstract class OnAppListChangedCallback : ObservableList.OnListChangedCallback<ObservableArrayList<App>>() {
//
//        abstract fun onAppListChanged(apps: ObservableArrayList<App>?)
//
//        override fun onChanged(p0: ObservableArrayList<App>?) {
//            Tool.print("1")
//        }
//
//        override fun onItemRangeChanged(p0: ObservableArrayList<App>?, p1: Int, p2: Int) {
//            Tool.print("2")
//        }
//
//        override fun onItemRangeInserted(p0: ObservableArrayList<App>?, p1: Int, p2: Int) {
//            Tool.print("3")
//            onAppListChanged(p0)
//        }
//
//        override fun onItemRangeMoved(p0: ObservableArrayList<App>?, p1: Int, p2: Int, p3: Int) {
//            Tool.print("4")
//        }
//
//        override fun onItemRangeRemoved(p0: ObservableArrayList<App>?, p1: Int, p2: Int) {
//            Tool.print("5")
//        }
//    }
}