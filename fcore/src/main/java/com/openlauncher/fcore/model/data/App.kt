package com.openlauncher.fcore.model.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import java.lang.Exception

data class App(
        private val packageManager: PackageManager,
        private var info: ResolveInfo,
        val label: String = info.loadLabel(packageManager).toString(),
        val packageName: String = info.activityInfo.packageName,
        val className: String = info.activityInfo.name,
        val icon: Drawable = info.loadIcon(packageManager)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as App

        if (info != other.info) return false
        if (packageManager != other.packageManager) return false
        if (label != other.label) return false
        if (packageName != other.packageName) return false
        if (className != other.className) return false

        return true
    }

    override fun hashCode(): Int {
        var result = packageManager.hashCode()
        result = 31 * result + info.hashCode()
        result = 31 * result + label.hashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + className.hashCode()
        result = 31 * result + icon.hashCode()
        return result
    }

    fun start(context: Context) {
        try {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setClassName(packageName, className)
            context.startActivity(intent)
        } catch (e: Exception) {
            throw e
        }
    }
}