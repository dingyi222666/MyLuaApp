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

class DefaultClickListLayoutHelper(rootView: View, dialog: BottomDialog) :
    BaseBottomDialogLayoutHelper(rootView, dialog) {

    private val binding = LayoutBottomDialogDefaultClickListBinding.bind(rootView)

    override fun getCloseView(): View {
        return binding.closeImage
    }

    override fun apply(params: BottomDialog.BottomDialogCreateParams) {
        if (params.items.isNotEmpty()) {
            setItemList(params)
        } else {
            binding.list.visibility = View.GONE
        }
        applyView(params)
    }

    private var clickCallBack: ((String, Any) -> Unit)? = null


    private fun applyView(params: BottomDialog.BottomDialogCreateParams) {

        if (params.title.isNotEmpty()) {
            binding.title.text = params.title
        } else {
            binding.title.visibility = View.GONE
        }

        if (params.negativeButtonText.isNotEmpty()) {
            binding.negativeButton.text = params.negativeButtonText
            binding.negativeButton.setOnClickListener {
                interceptClose(true)
                params.negativeButtonClick(
                    this, getSelectItemValue(params)
                )
                dismiss()
            }
        } else {
            binding.negativeButton.visibility = View.GONE
        }

        if (params.neutralButtonText.isNotEmpty()) {

            binding.neutralButton.text = params.neutralButtonText
            binding.neutralButton.setOnClickListener {
                interceptClose(true)
                params.neutralButtonClick(this, getSelectItemValue(params))
                dismiss()
            }
        } else {
            binding.neutralButton.visibility = View.GONE
        }

        if (params.positiveButtonText.isNotEmpty()) {

            binding.positiveButton.text = params.positiveButtonText
            binding.positiveButton.setOnClickListener {
                interceptClose(true)
                params.positiveButtonClick(this, getSelectItemValue(params))

                dismiss()
            }
        } else {
            binding.positiveButton.visibility = View.GONE
        }
    }

    private fun getSelectItemValue(params: BottomDialog.BottomDialogCreateParams): Pair<String, Any> {
        return params.items[
                binding.list.adapter?.convertObject<ListAdapter>()?.selectIndex ?: 0
        ]
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setItemList(params: BottomDialog.BottomDialogCreateParams) {

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
                addAll(params.items)
                setChecked(params.defaultChoiceItem[0])
            }
            ?.notifyDataSetChanged()

    }


    private inner class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

        private val list = mutableListOf<Pair<String, Any>>()

        var selectIndex = 0

        private var clickCallBack: ((String, Any) -> Unit)? = null

        val setItemClick = { clickCallBack: ((String, Any) -> Unit)? ->
            this.clickCallBack = clickCallBack
        }

        fun addAll(list: List<Pair<String, Any>>) {
            this.list.addAll(list)
        }

        private inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
            holder.binding.text.text = list[position].first
            holder.binding.root.setOnClickListener {
                holder.binding.radioButton.toggle()
                clickCallBack?.invoke(list[position].first, list[position].second)
            }
            holder.binding.radioButton.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectIndex = position
                    if (!binding.list.isComputingLayout) {
                        notifyDataSetChanged()
                    }
                }
            }
            holder.binding.radioButton.isChecked = position == selectIndex
        }

        override fun getItemCount(): Int {
            return list.size
        }

        fun setChecked(index: Int) {
            selectIndex = index
            notifyDataSetChanged()
        }

    }
}