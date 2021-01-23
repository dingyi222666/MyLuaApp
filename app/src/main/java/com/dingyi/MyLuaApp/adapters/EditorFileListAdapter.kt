package com.dingyi.MyLuaApp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.dingyi.MyLuaApp.R
import com.dingyi.MyLuaApp.databinding.DialogFlieListBinding
import com.dingyi.MyLuaApp.utils.EditorUtil
import com.dingyi.MyLuaApp.utils.getSuffix
import com.dingyi.MyLuaApp.utils.toFile
import java.io.File

class EditorFileListAdapter(val context: Context) : BaseAdapter() {

    private val data = mutableListOf<File>()
    private var projectPath = ""
    private var nowOpenPath = ""
    var editorUtil:EditorUtil?=null;
    private fun getImageType(file: File): Int {
        val data = mapOf("lua,java,aly,gradle,xml,py" to R.drawable.ic_twotone_code_24,
                "default" to R.drawable.ic_twotone_insert_drive_file_24,
                "png,jpg,bmp" to R.drawable.ic_twotone_image_24,
                "dir" to R.drawable.ic_twotone_folder_24)

        if (file.isDirectory) return data["dir"]!!
        if (file.path=="...") return R.drawable.ic_twotone_undo_24
        data.forEach {
            if (it.key.lastIndexOf(getSuffix(file.name)) != -1) {
                return it.value
            }
        }

        return data["default"]!!

    }

    fun setOnItemClick(listView: ListView) {
        listView.onItemClickListener = AdapterView.OnItemClickListener { _: AdapterView<*>, view: View, i: Int, _: Long ->
            when ((view.tag as ViewHolder).title.text) {
                "..." -> load(projectPath,nowOpenPath.toFile().parentFile.path)
                else -> {
                    if (data[i].isDirectory) {
                        load(projectPath,data[i].path)
                    } else {
                        editorUtil?.let {
                            it.openFile(data[i].path)
                        }
                    }
                }
            }

        }
    }

    fun load(projectPath: String, path: String) {
        this.projectPath = projectPath
        nowOpenPath = path
        data.clear();
        notifyDataSetChanged()
        data.addAll(path.toFile().listFiles()!!)
        data.sortDescending()
        if (projectPath!=path) {
            data.add(0,File("..."))
        }
        notifyDataSetChanged();
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var title = item.findViewById<TextView>(R.id.title)
        var icon = item.findViewById<ImageView>(R.id.icon)
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = DialogFlieListBinding.inflate(LayoutInflater.from(context), parent, false).root
            view.tag = ViewHolder(view)
        }

        val holder = view.tag as ViewHolder
        holder.title.text = data[position].name
        holder.icon.setImageResource(getImageType(data[position]))
        return view
    }


}