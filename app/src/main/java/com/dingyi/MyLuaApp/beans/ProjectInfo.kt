package com.dingyi.MyLuaApp.bean

import android.os.Parcel
import android.os.Parcelable

data class ProjectInfo(val projectPath:String, val projectType:Int,
                       val projectName:String, val projectVersionName:String,
                       val projectVersionCode:String, val projectPackageName:String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readInt(),
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(projectPath)
        parcel.writeInt(projectType)
        parcel.writeString(projectName)
        parcel.writeString(projectVersionName)
        parcel.writeString(projectVersionCode)
        parcel.writeString(projectPackageName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectInfo> {
        override fun createFromParcel(parcel: Parcel): ProjectInfo {
            return ProjectInfo(parcel)
        }

        override fun newArray(size: Int): Array<ProjectInfo?> {
            return arrayOfNulls(size)
        }
    }


}
