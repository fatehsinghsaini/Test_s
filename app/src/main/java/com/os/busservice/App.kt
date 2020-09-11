package com.os.busservice

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp

class App :MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        singleton=this
        MultiDex.install(this)
        FirebaseApp.initializeApp(this)


    }

    companion object{
        var singleton:App?=null
    }

}