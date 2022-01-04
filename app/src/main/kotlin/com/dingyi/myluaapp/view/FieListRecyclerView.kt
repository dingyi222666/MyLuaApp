package com.dingyi.myluaapp.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dingyi.myluaapp.R
import com.dingyi.myluaapp.common.kts.*
import com.dingyi.myluaapp.databinding.LayoutItemEditorFileListBinding
import com.drake.brv.BindingAdapter
import com.drake.brv.annotaion.AnimationType
import com.drake.brv.item.ItemBind
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.jvm.functions.FunctionN

class FieListRecyclerView(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {


    private var titleView: TextView? = null

    init {
        linear()
            .setup {
                addType<FileListModel>(R.layout.layout_item_editor_file_list)
            }
            .onBind {
                itemView.setOnClickListener {
                    val model = getModel<FileListModel>()
                    if (model.file.isDirectory || model.file.path == "...") {
                        callbackMap[0x03]?.convertObject<(String)->Unit>()?.invoke(model.file.path)
                    }
                    if (model.file.isFile) {
                        callbackMap[0x02]?.convertObject<(String)->Unit>()?.invoke(model.file.path)
                    }
                }
                itemView.setOnLongClickListener {
                    val model = getModel<FileListModel>()
                    if (model.file.path != "...") {
                        callbackMap[0x04]?.convertObject<(View,String)->Unit>()?.invoke(it,model.file.path)
                    }
                    true
                }
            }
    }

    private val callbackMap = mutableMapOf<Int,Any>()


    val onEnterFile = { function: (String) -> Unit ->
        callbackMap[0x01] = function
    }

    val onClickFile = { function: (String) -> Unit ->
        callbackMap[0x02] = function
    }

    val onLongClick = { function: (View, String) -> Unit ->
        callbackMap[0x04] = function
    }

    val onEnterDir = { function: (String) -> Unit ->
        callbackMap[0x03] = function
    }

    var projectPath = ""

    private var lastOpenedDir = ""

    val nowOpenDir: String
        get() = lastOpenedDir

    suspend fun refreshFileList() = loadFileList(lastOpenedDir)

    suspend fun loadFileList(path: String) = withContext(Dispatchers.IO) {
        runCatching {
            val dir =
                if (path == "...") lastOpenedDir.toFile().parentFile?.absolutePath
                    ?: path else path.toFile().absolutePath

            Log.e("error", dir)

            dir.toFile()
                .apply {
                    lastOpenedDir = dir
                    val file = this
                    withContext(Dispatchers.Main) {
                        titleView?.text = file.absolutePath
                    }
                }
                .listFiles()
                .run {
                    this ?: arrayOf<File>()
                }
                .toMutableList()
                .apply {
                   if (dir != projectPath) {
                        add(0, File("..."))
                    }
                }
                .sortBySelf()
                .map(::FileListModel)
                .apply {
                    val list = this
                    //切换到主线程
                    withContext(Dispatchers.Main) {
                        bindingAdapter.setAnimation(AnimationType.ALPHA)
                        bindingAdapter.animationEnabled = true
                        bindingAdapter.models = list
                        callbackMap[0x01]?.convertObject<(String)->Unit>()?.invoke(dir)
                    }
                }



        }.onFailure {
            it.printStackTrace(System.err)
        }
            .isSuccess

    }

    fun bindTitleView(textView: TextView) {
        titleView = textView
    }

    class FileListModel(val file: File) : ItemBind {

        companion object {
            private var imageData = mapOf(
                "lua,java,aly,gradle,xml,py" to R.drawable.ic_twotone_code_24,
                "default" to R.drawable.ic_twotone_insert_drive_file_24,
                "png,jpg,bmp" to R.drawable.ic_twotone_image_24,
                "dir" to R.drawable.ic_twotone_folder_24
            )

            private fun getImageResId(path: String): Int {
                val file = path.toFile()
                if (file.isDirectory)
                    return imageData.getValue("dir")

                if (path == "...")
                    return R.drawable.ic_twotone_undo_24


                imageData.forEach {
                    if (it.key.lastIndexOf(file.suffix) != -1) {
                        return it.value
                    }
                }

                return imageData.getValue("default")
            }
        }

        override fun onBind(holder: BindingAdapter.BindingViewHolder) {
            val binding = LayoutItemEditorFileListBinding.bind(holder.itemView)
            binding.title.text = file.name
            Glide.with(binding.image)
                .load(getImageResId(file.path))
                .into(binding.image)

        }


    }
}