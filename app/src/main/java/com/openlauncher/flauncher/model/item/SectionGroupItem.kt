package com.openlauncher.flauncher.model.item

import android.support.annotation.NonNull
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.openlauncher.fcore.model.data.App
import com.openlauncher.flauncher.R
import kotlinx.android.synthetic.main.item_section_group.view.*

class SectionGroupItem(var title: String, apps: ArrayList<App>) : AbstractItem<SectionGroupItem, SectionGroupItem.ViewHolder>() {

    val itemAdapter = FastItemAdapter<AppItem>()
    val items = apps.map { AppItem(it) }

    override fun getType(): Int = 0

    override fun getLayoutRes(): Int = R.layout.item_section_group

    override fun getViewHolder(@NonNull v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<SectionGroupItem>(view) {

        override fun bindView(item: SectionGroupItem, payloads: List<Any>) {
            itemView.item_section_group_title.text = item.title

            itemView.item_section_group_recycler.layoutManager = LinearLayoutManager(itemView!!.context,LinearLayoutManager.HORIZONTAL,false)
            itemView.item_section_group_recycler.adapter = item.itemAdapter
            item.itemAdapter.add(item.items)
            item.itemAdapter.withOnClickListener { v, _, innerItem, _ ->
                innerItem.app.start(v!!.context)
                true
            }
        }

        override fun unbindView(item: SectionGroupItem) {
            itemView.item_section_group_title.text = null

            item.itemAdapter.clear()
            item.itemAdapter.withOnClickListener(null)
            itemView.item_section_group_recycler.adapter = null
            itemView.item_section_group_recycler.layoutManager = null
        }
    }
}