package com.dingyi.myluaapp.common.dialog.helper

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.myluaapp.common.dialog.BottomDialog
import com.dingyi.myluaapp.common.kts.convertObject
import com.dingyi.myluaapp.common.kts.ifNull
import com.dingyi.myluaapp.databinding.LayoutBottomDialogDefaultClickListBinding
import com.dingyi.myluaapp.databinding.LayoutItemBottomDialogDefaultClickItemBinding
import kotlin.properties.Delegates

class DefaultClickListLayoutHelper(rootView: View) : BaseBottomDialogLayoutHelper(rootView) {

    private val binding = LayoutBottomDialogDefaultClickListBinding.bind(rootView)

    override fun getCloseView(): View {
        return binding.closeImage
    }

    override fun apply(params: BottomDialog.BottomDialogCreateParams) {

    }

    private var clickCallBack: ((String, Any) -> Unit)? = null

    /*
    @SuppressLint("NotifyDataSetChanged")
    override fun setItemList(list: List<Pair<String, Any>>) {

        binding.list.adapter.ifNull {
            binding.list.adapter = ListAdapter()
        }

        binding.list.layoutManager.ifNull {
            binding.list.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
        }

        binding.list.adapter?.convertObject<ListAdapter>()
            ?.apply {
                setItemClick(clickCallBack)
                addAll(list)
            }
            ?.notifyDataSetChanged()

    }

     */

    class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        private val list = mutableListOf<Pair<String, Any>>()

        private var clickCallBack: ((String, Any) -> Unit)? = null

        val setItemClick = { clickCallBack: ((String, Any) -> Unit)? ->
            this.clickCallBack = clickCallBack
        }

        fun addAll(list: List<Pair<String, Any>>) {
            this.list.addAll(list)
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val binding = LayoutItemBottomDialogDefaultClickItemBinding.bind(itemView)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutItemBottomDialogDefaultClickItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ).root
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.binding.item.text = list[position].first
            holder.binding.root.setOnClickListener {
                clickCallBack?.invoke(list[position].first, list[position].second)
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }
}