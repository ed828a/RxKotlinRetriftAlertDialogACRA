package com.example.edward.rxkotlinapp.model

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.StringRes

/**
 * Created by Edward on 1/12/2019.
 */
//data class ErrorInfo(val userAction: UserAction,
//                     val serviceName: String?,
//                     val request: String?,
//                     @StringRes val message: Int): Parcelable {
//
//    constructor(parcel: Parcel) : this(
//        UserAction.valueOf(parcel.readString()!!),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readInt()
//    ) {
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(userAction.name)
//        parcel.writeString(serviceName)
//        parcel.writeString(request)
//        parcel.writeInt(message)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<ErrorInfo> {
//        override fun createFromParcel(parcel: Parcel): ErrorInfo {
//            return ErrorInfo(parcel)
//        }
//
//        override fun newArray(size: Int): Array<ErrorInfo?> {
//            return arrayOfNulls(size)
//        }
//    }
//}
//
//
//enum class UserAction(val message: String?) {
//    USER_REPORT("user report"),
//    UI_ERROR("ui error"),
//    SUBSCRIPTION("subscription"),
//    LOAD_IMAGE("load image"),
//    SOMETHING_ELSE("something else"),
//    SEARCHED("searched"),
//    GET_SUGGESTIONS("get suggestions"),
//    REQUESTED_STREAM("requested stream"),
//    REQUESTED_CHANNEL("requested channel"),
//    REQUESTED_PLAYLIST("requested playlist"),
//    REQUESTED_KIOSK("requested kiosk"),
//    DELETE_FROM_HISTORY("delete from history"),
//    PLAY_STREAM("Play stream"),
//    NULL_VALUE(null)
//}
//
