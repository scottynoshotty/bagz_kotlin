package com.eridiumcorp.bagz

import android.app.Application
import com.google.firebase.Firebase
import com.google.firebase.initialize
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class BagzApp : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BagzApp)
            modules(appModule)
        }
        Firebase.initialize(this)
    }
}