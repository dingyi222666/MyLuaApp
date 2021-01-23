package com.dingyi.MyLuaApp.bean

import android.os.Parcel
import android.os.Parcelable

data class ProjectInfo(val path: String?, val type:Int, val name: String?, val versionName: String?, val versionCode: String?, val packageName: String?) : Parcelable {


    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {

    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        parcel.writeInt(type)
        parcel.writeString(name)
        parcel.writeString(versionName)
        parcel.writeString(versionCode)
        parcel.writeString(packageName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectInfo> {
        val LUA_PEOJECT=0xff

        val GRADLE_PROJECT=0x1f

        override fun createFromParcel(parcel: Parcel): ProjectInfo {
            return ProjectInfo(parcel)
        }

        override fun newArray(size: Int): Array<ProjectInfo?> {
            return arrayOfNulls(size)
        }
    }
}
