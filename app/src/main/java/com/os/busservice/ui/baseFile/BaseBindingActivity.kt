package com.os.busservice.ui.baseFile


import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facebook.*
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.os.busservice.R
import com.os.busservice.model.FbDetails
import com.os.busservice.model.SocialModel
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.SessionManager
import com.os.busservice.utility.Tags


abstract class BaseBindingActivity : AppCompatActivity(){
    var mSavedInstanceState: Bundle? = null
    protected var mActivity: AppCompatActivity? = null
    protected var sessionManager: SessionManager? = null
    protected var mDatabase: DatabaseReference? = null
    //get location
    protected abstract fun setBinding()
    protected abstract fun createActivityObject()
    protected abstract fun initializeObject()
    protected abstract fun setListeners()
    private var mLoginViewModel: LoginViewModel? = null

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSavedInstanceState = savedInstanceState
        sessionManager = SessionManager.getInstance(this)
        FirebaseApp.initializeApp(this)
        mDatabase = FirebaseDatabase.getInstance().reference
        createActivityObject()
        setBinding()
        initializeObject()
        setListeners()
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (mActivity == null) throw NullPointerException("must create activty object")
    }

/*    fun changeFragment(fragment: Fragment, isAddToBack: Boolean) {
        if (fragment is ShipHistoryTabFragment) {
            toolbarProfilePic.visibility = View.GONE
            toolbarBackBt.visibility = View.VISIBLE
        } else {
            toolbarProfilePic.visibility = View.VISIBLE
            toolbarBackBt.visibility = View.GONE
        }

        this.fragment = fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment, fragment.javaClass.name)
        if (isAddToBack && supportFragmentManager.findFragmentByTag(fragment.tag) == null) transaction.addToBackStack(
            fragment.javaClass.name
        )
        transaction.commit()
    }*/

    override fun onBackPressed() {
        super.onBackPressed()
        Log.e("onBackPressed", "call")
    }


    fun startActivity(activity: Activity) {
        startActivity(Intent(mActivity, activity::class.java))
        finish()
    }

    private var callbackManager: CallbackManager? = null
    var socialModel: SocialModel? = null
    private var isCalledOnce: Boolean? = false
    private var mAuth: FirebaseAuth? = null
    private val RC_SIGN_IN = 9001
    private var mGoogleSignInClient: GoogleSignInClient? = null

    fun faceBookLogin(login: String?) {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance()
            .logInWithReadPermissions(
                this,
                listOf("public_profile", "email")
            )
        LoginManager.getInstance().registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    AppDelegate.LogDB("login success" + loginResult.accessToken + "")
                    AppDelegate.LogT("onSuccess = " + loginResult.accessToken + "")
                    ProgressDialog.showProgressDialog(mActivity!!)
                    var fb_LoginToken: String = loginResult.accessToken.toString()
                    val request = GraphRequest.newMeRequest(
                        loginResult.accessToken
                    ) { _, response ->
                        if (response != null) {
                            socialModel = SocialModel()
                            socialModel =
                                FbDetails().getFacebookDetail(response.jsonObject.toString())
                            AppDelegate.LogT("Facebook details==" + socialModel + "")
                            ProgressDialog.hideProgressDialog()
                            /*if (login == Tags.LOGIN)
                            {*/
                            mLoginViewModel?.firstName?.value = socialModel?.first_name
                            mLoginViewModel?.lastName?.value = socialModel?.last_name
                            mLoginViewModel?.emial?.value = socialModel?.email_address
                            mLoginViewModel?.socialLoginApi(
                                sessionManager?.mFCMToken,
                                socialModel?.socialId,
                                "", socialModel
                            )
                            //  }
                            /* else {
                                mLoginViewModel?.emial?.value = socialModel?.email_address
                                mLoginViewModel?.firstName?.value ="${socialModel?.first_name} ${socialModel?.last_name}"
                            }*/
                        }
                    }
                    val parameters = Bundle()
                    parameters.putString(
                        "fields",
                        "first_name,last_name,email,id,name,gender,birthday,picture.type(large)"
                    )
                    request.parameters = parameters
                    request.executeAsync()
                }

                override fun onCancel() {
                    AppDelegate.LogDB("login cancel")

                    if (AccessToken.getCurrentAccessToken() != null)
                        LoginManager.getInstance().logOut()
                    if (!isCalledOnce!!) {
                        isCalledOnce = true
                        faceBookLogin(Tags.LOGIN)
                    }
                }

                override fun onError(exception: FacebookException) {
                    AppDelegate.LogDB("login error = " + exception.message)
                    if (exception.message!!.contains("CONNECTION_FAILURE")) {
                        ProgressDialog.hideProgressDialog()
                    } else if (exception is FacebookAuthorizationException) {
                        if (AccessToken.getCurrentAccessToken() != null) {
                            LoginManager.getInstance().logOut()
                            if (!isCalledOnce!!) {
                                isCalledOnce = true
                                faceBookLogin(Tags.LOGIN)
                            }
                        }
                    }
                }
            })
    }

    var mSocialType: String? = ""
    fun googlePlusLogin(login: String?) {
        mSocialType = login
/*        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()*/

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        if (mGoogleSignInClient == null)
            mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        mAuth = FirebaseAuth.getInstance()
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            AppDelegate.LogT("resultCode==>" + resultCode)
            AppDelegate.LogT("data==>" + data)
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            try {
                AppDelegate.Log("Error is: ", result?.status.toString())
                if (result!!.isSuccess) {
                    val account = result.signInAccount
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                AppDelegate.LogE(e)
            }
        } else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            callbackManager?.onActivityResult(requestCode, resultCode, data)
        }

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        AppDelegate.LogT("firebaseAuthWithGoogle:" + acct!!.id!!)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    AppDelegate.LogT("signInWithCredential:success")
                    val user = mAuth!!.currentUser
                    socialModel = SocialModel()
                    socialModel!!.email_address = user?.email!!
                    socialModel!!.loginType = Tags.google
                    socialModel!!.socialId = user.uid
                    socialModel!!.first_name = user.displayName!!
                    socialModel!!.image = user.photoUrl.toString()


                  /*  if (mSocialType == Tags.LOGIN)
                        mLoginViewModel?.socialLoginApi(
                            sessionManager?.mFCMToken,
                            "",
                            socialModel?.socialId
                        )
                    else {
                        mLoginViewModel?.emial?.value = socialModel?.email_address
                        mLoginViewModel?.firstName?.value =
                            "${socialModel?.first_name} ${socialModel?.last_name}"
                    }*/


                    mLoginViewModel?.firstName?.value =socialModel?.first_name
                    mLoginViewModel?.lastName?.value =socialModel?.last_name
                    mLoginViewModel?.emial?.value =socialModel?.email_address
                    mLoginViewModel?.socialLoginApi(
                        sessionManager?.mFCMToken,
                        "",
                        socialModel?.socialId,
                        socialModel
                    )

                    signOut()
                } else {
                    AppDelegate.LogT("signInWithCredential:failure" + task.exception)
                    AppDelegate.showToast(mActivity, "Authentication failed.")
                }
            }
    }

    private fun signOut() {
        if (mGoogleSignInClient != null) {
            mAuth!!.signOut()
            mGoogleSignInClient!!.signOut()
        }
    }

    fun changeFragment(fragment: Fragment, isAddToBack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout, fragment, fragment.javaClass.name)
        if (isAddToBack && supportFragmentManager.findFragmentByTag(fragment.tag) == null) transaction.addToBackStack(
            fragment.javaClass.name
        )
        transaction.commit()
    }


    fun changeToolbarTitle(title: String) {

    }


}

