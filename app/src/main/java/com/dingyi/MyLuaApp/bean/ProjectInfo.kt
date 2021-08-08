package com.dingyi.MyLuaApp.bean

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
    val appPackageName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProjectInfo

        if (iconPath != other.iconPath) return false
        if (appName != other.appName) return false
        if (appPackageName != other.appPackageName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = iconPath.hashCode()
        result = 31 * result + appName.hashCode()
        result = 31 * result + appPackageName.hashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(iconPath)
        parcel.writeString(appName)
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

