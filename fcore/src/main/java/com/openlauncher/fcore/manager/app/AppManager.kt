package com.openlauncher.fcore.manager.app

import android.content.Intent
import android.content.pm.PackageManager
import com.openlauncher.fcore.model.data.App
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import rx.schedulers.Schedulers
import java.text.Collator

object AppManager {

    var packageManager: PackageManager? = null
    private var allApps: ArrayList<App>? = null

    /**
     * Must call this before calling
     * @see getAllApps
     * else an
     * @see IllegalStateException
     * will be thrown
     */
    fun init(packageManager: PackageManager) {
        AppManager.packageManager = packageManager
    }

    /**
     * This will return an Observable with an ArrayList<App> as result
     */
    fun getAllApps(force: Boolean = false): Observable<ArrayList<App>> {
        return Observable.fromCallable {
            if (packageManager == null) throw IllegalStateException("Have not init yet, please call init")
            if (allApps != null && !force) return@fromCallable allApps!!

            if (allApps == null)
                allApps = ArrayList()
            else
                allApps?.clear()

            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val activitiesInfo = packageManager!!.queryIntentActivities(intent, 0)
            activitiesInfo.sortWith(Comparator { p1, p2 -> Collator.getInstance().compare(p1.loadLabel(packageManager).toString(), p2.loadLabel(packageManager).toString()) })

            activitiesInfo
                    .map { App(packageManager!!, it) }
                    .forEach { allApps!!.add(it) }

            return@fromCallable allApps!!
        }
//                //This is a background task
//                .subscribeOn(Schedulers.io())
//                //We will receive the result in the UI thread
//                .observeOn(AndroidSchedulers.mainThread())
    }
}