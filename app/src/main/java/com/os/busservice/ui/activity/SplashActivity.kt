package com.os.busservice.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.os.busservice.MainActivity
import com.os.busservice.R
import com.os.busservice.utility.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
    }

    private fun initView() {
        val mSessionManager = SessionManager.getInstance(this)
        FirebaseApp.initializeApp(this)
        keyHashGen()
        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)

          /*  val intent = if (mSessionManager!!.isLogin!!)
                Intent(baseContext, MainActivity::class.java)
            else
                Intent(baseContext, LocationActivity::class.java)*/

            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
            finish()

        }
    }

    private fun keyHashGen() {

        // Add code to print out the key hash
        // Add code to print out the key hash
        try {
            val info = packageManager.getPackageInfo(
                "com.os.shiop",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    "KeyHash:",
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

}