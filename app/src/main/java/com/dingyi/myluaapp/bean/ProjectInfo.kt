package com.dingyi.myluaapp.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * @author: dingyi
 * @date: 2021/8/7 15:54
 * @description:
 **/
data class ProjectInfo(
    val iconPath: String,
    val appName: String,
    val path:String,
    val appPackageName: String
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(iconPath)
        parcel.writeString(appName)
        parcel.writeString(path)
        parcel.writeString(appPackageName)
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
