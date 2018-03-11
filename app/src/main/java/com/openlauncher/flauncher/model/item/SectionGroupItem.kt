package com.openlauncher.flauncher.model.item

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.NonNull
import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.openlauncher.fcore.model.data.App
import com.openlauncher.flauncher.R

//Draft
class SectionGroupItem(val apps: ArrayList<App>) : AbstractItem<SectionGroupItem, SectionGroupItem.ViewHolder>() {

    override fun getType(): Int = 0

    override fun getLayoutRes(): Int = R.layout.item_app

    override fun getViewHolder(@NonNull v: View): ViewHolder = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<SectionGroupItem>(view) {

        private val binding : ViewDataBinding = DataBindingUtil.bind(view)

        override fun bindView(item: SectionGroupItem, payloads: List<Any>) {
            //binding.setVariable(BR.item,item.app)
            binding.executePendingBindings()
        }

        override fun unbindView(item: SectionGroupItem) {

        }
    }
}