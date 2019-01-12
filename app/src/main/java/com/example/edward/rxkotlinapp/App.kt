package com.example.edward.rxkotlinapp

import android.app.Application
import android.content.Context
import org.acra.ACRA
import org.acra.ReportingInteractionMode
import org.acra.annotation.ReportsCrashes

/**
 * Created by Edward on 1/12/2019.
 */
@ReportsCrashes(
    mailTo = "ed828a@gmail.com",
    mode = ReportingInteractionMode.SILENT,
    resToastText = R.string.crash_toast_text
)
class App: Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        ACRA.init(this)
    }
}