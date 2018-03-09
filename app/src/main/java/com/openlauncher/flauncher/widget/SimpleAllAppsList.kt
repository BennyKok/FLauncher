package com.openlauncher.flauncher.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.openlauncher.fcore.manager.app.AppManager
import com.openlauncher.flauncher.model.recycler.AppItem

class SimpleAllAppsList @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = GridLayoutManager(context, 4)
        val fastItemAdapter = FastItemAdapter<AppItem>()
        adapter = fastItemAdapter

        if (!isInEditMode)
            AppManager.getAllApps().subscribe { allApps ->
                fastItemAdapter.set(allApps!!.map { AppItem(it) })
                fastItemAdapter.withOnClickListener { _, _, item, _ ->
                    item.app.start(context)
                    true
                }
            }
    }
}