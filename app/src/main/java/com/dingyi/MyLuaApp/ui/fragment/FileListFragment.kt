package com.dingyi.MyLuaApp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dingyi.MyLuaApp.databinding.FragmentFileListBinding

class FileListFragment: Fragment() {

    var binding:FragmentFileListBinding?=null;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding=FragmentFileListBinding.inflate(inflater,container,false);
        return binding?.root;
    }



}