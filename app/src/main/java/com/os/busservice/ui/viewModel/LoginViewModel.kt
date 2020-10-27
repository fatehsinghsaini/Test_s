package com.os.busservice.ui.viewModel

import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os.busservice.App
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.data.datasource.UserDataSource
import com.os.busservice.data.retro.RestApiFactory
import com.os.busservice.model.CommonResponse
import com.os.busservice.model.RequestModel
import com.os.busservice.model.SocialModel
import com.os.busservice.model.loginResponse.LoginResponse
import com.os.busservice.model.loginResponse.LoginResult
import com.os.busservice.ui.activity.RegisterActivity
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.Tags
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel : ViewModel() {

    var userDataSource: UserDataSource? = null
    var firstName = MutableLiveData<String>()
    var lastName = MutableLiveData<String>()
    var termsConditionFlag = MutableLiveData<Boolean>()
    var mobileNo = MutableLiveData<String>()
    var fbId = MutableLiveData<String>()
    var googleId = MutableLiveData<String>()
    var mOldPassword = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confirmPwd = MutableLiveData<String>()
    var referral_by = MutableLiveData<String>()
    var emial = MutableLiveData<String>()
    var countryCode = MutableLiveData<String>()
    var pwdShowingFlag = MutableLiveData<Boolean>()

    var error = MutableLiveData<String>()
    var loginLiveData = MutableLiveData<ApiResponse<LoginResponse>>()
    var otpLiveData = MutableLiveData<ApiResponse<LoginResponse>>()
    var commonLiveData = MutableLiveData<ApiResponse<CommonResponse>>()
    var logoutLiveData = MutableLiveData<ApiResponse<CommonResponse>>()

    private var restApiFactory: RestApiFactory? = null
    var apiResponse: ApiResponse<LoginResponse>? = null
    var commonApiResponse: ApiResponse<CommonResponse>? = null

    private var subscription: Disposable? = null


    init {
        restApiFactory = RestApiFactory
        userDataSource = UserDataSource(restApiFactory!!.create())
        apiResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)
        commonApiResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)
    }

    private fun isValidSocialLogin(): Boolean {
        val app = App.singleton
        if (TextUtils.isEmpty(firstName.value)) {
            error.value = app!!.getString(R.string.enter_your_name)
            return false
        } else if (TextUtils.isEmpty(mobileNo.value)) {
            error.value = app!!.getString(R.string.enter_phone_number)
            return false
        } else if (TextUtils.isEmpty(emial.value)) {
            error.value = app!!.getString(R.string.enter_email)
            return false
        } else if (!AppDelegate.isEmailValid(emial.value!!)) {
            error.value = app!!.getString(R.string.valid_email)
            return false
        }else if (termsConditionFlag.value==null || !termsConditionFlag.value!!) {
            error.value = app!!.getString(R.string.please_accept_terms_conditon)
            return false
        }

        return true
    }
    private fun isValidUserData(): Boolean {
        val app = App.singleton
        if (TextUtils.isEmpty(firstName.value)) {
            error.value = app!!.getString(R.string.enter_your_name)
            return false
        }else if (TextUtils.isEmpty(lastName.value)) {
            error.value = app!!.getString(R.string.enter_last_name)
            return false
        } else if (firstName.value!!.length !in 3..30) {
            error.value = app!!.getString(R.string.name_validation)
            return false
        } else if (TextUtils.isEmpty(mobileNo.value)) {
            error.value = app!!.getString(R.string.enter_phone_number)
            return false
        }
        else if (mobileNo.value!!.length !in 7..15) {
            error.value = app!!.getString(R.string.enter_valid_phone_number)
            return false
        }
        else if (TextUtils.isEmpty(emial.value)) {
            error.value = app!!.getString(R.string.enter_email)
            return false
        }
        else if (!AppDelegate.isEmailValid(emial.value!!)) {
            error.value = app!!.getString(R.string.valid_email)
            return false
        }
        else if (pwdShowingFlag.value!! && TextUtils.isEmpty(password.value)) {
            error.value = app!!.getString(R.string.enter_password)
            return false
        } else if (pwdShowingFlag.value!! && TextUtils.isEmpty(confirmPwd.value)) {
            error.value = app!!.getString(R.string.enter_confirm_password)
            return false
        }  else if (pwdShowingFlag.value!! && password.value!!.length<6) {
            error.value = app!!.getString(R.string.password_length_validation)
            return false
        } else if (pwdShowingFlag.value!! && !AppDelegate.isValidPassword(password.value!!)) {
            error.value = app!!.getString(R.string.invalid_password)
            return false
        } else if (pwdShowingFlag.value!! && password.value != confirmPwd.value) {
            error.value = app!!.getString(R.string.password_confirm_password_not_same)
            return false
        }else if (termsConditionFlag.value==null || !termsConditionFlag.value!!) {
            error.value = app!!.getString(R.string.please_accept_terms_conditon)
            return false
        }
        return true
    }

    private fun isValidUserDataCP(): Boolean {
        val app = App.singleton

        if (TextUtils.isEmpty(mOldPassword.value)) {
            error.value = app!!.getString(R.string.enter_old_password)
            return false
        } else if (TextUtils.isEmpty(password.value)) {
            error.value = app!!.getString(R.string.enter_password)
            return false
        } else if (TextUtils.isEmpty(confirmPwd.value)) {
            error.value = app!!.getString(R.string.enter_confirm_password)
            return false
        } else if (!AppDelegate.isValidPassword(password.value!!)) {
            error.value = app!!.getString(R.string.invalid_password)
            return false
        } else if (password.value != confirmPwd.value) {
            error.value = app!!.getString(R.string.password_confirm_password_not_same)
            return false
        }
        return true
    }


    fun socialLoginApi(
        deviceToken: String?,
        fbId: String?,
        googleId: String?,
        socialModel: SocialModel?
    ) {
        val request = RequestModel()
        request.google_id = googleId
        request.fb_id = fbId
        request.device_token = deviceToken
        request.email = emial.value
        request.mobile = mobileNo.value
        request.country_code = countryCode.value
        request.device_type = Tags.android
        request.language = "en"

        if (referral_by.value.toString().trim() == "null")
            request.referred_by = ""
        else
            request.referred_by = referral_by.value.toString().trim()

        subscription = userDataSource!!.socialLogin(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                loginLiveData.postValue(apiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    if(!result.accountNotExist!!)
                    {
                        val data=LoginResult("","","","", ""
                            ,"","","","","","","","",
                            "","","","",0,"","")
                        data.email =emial.value
                        if(firstName.value!=null)
                            data.first_name = firstName.value
                        if(lastName.value!=null)
                            data.last_name = lastName.value
                        val ctx=App.singleton
                        ctx?.startActivity(Intent(ctx, RegisterActivity::class.java).putExtra(Tags.DATA,data).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).putExtra(Tags.LOGIN,socialModel))
                    }


                    loginLiveData.postValue(apiResponse!!.success(result))
                },
                { throwable ->
                    loginLiveData.postValue(apiResponse!!.error(throwable))
                }
            )

    }

    fun loginApi(deviceId: String?) {

        val app = App.singleton
        if (TextUtils.isEmpty(emial.value)) {
            error.value = app!!.getString(R.string.email_contect_no_required)
            return
        } else if (TextUtils.isEmpty(password.value)) {
            error.value = app!!.getString(R.string.enter_password)
            return
        }

        val request = RequestModel()
        request.email = emial.value
        request.password = password.value
        request.device_token = deviceId
        request.device_type = Tags.android
        request.language = "en"

        subscription = userDataSource!!.mLogin(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                loginLiveData.postValue(apiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    loginLiveData.postValue(apiResponse!!.success(result))
                },
                { throwable ->
                    loginLiveData.postValue(apiResponse!!.error(throwable))
                }
            )
    }

    fun registerApi(deviceToken: String?, result: LoginResult?) {
        val request = RequestModel()
        request.firstName = result?.first_name
        request.lastName = result?.last_name
        request.mobile = result?.mobile
        request.country_code = result?.country_code
        request.email = result?.email
        request.password = result?.password
        request.device_type = Tags.android
        request.language = "en"
        request.device_token = deviceToken
        request.otp = result?.otp
        request.fb_id=result?.fb_id
        request.google_id=result?.google_id

        if (referral_by.value.toString().trim() == "null")
            request.referred_by = ""
        else
            request.referred_by = referral_by.value.toString().trim()

        subscription = userDataSource!!.mSignUp(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                loginLiveData.postValue(apiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    loginLiveData.postValue(apiResponse!!.success(result))
                },
                { throwable ->
                    loginLiveData.postValue(apiResponse!!.error(throwable))
                }
            )

    }


    fun mSendOtp(registerFlag: String) {
        if (registerFlag==Tags.REGISTER && !isValidUserData())
            return

       val mCountryCode= countryCode.value
        mCountryCode?.replace("+","")

        val request = RequestModel()
        request.country_code = mCountryCode
        request.mobile = mobileNo.value
        request.language = "en"

        subscription = userDataSource!!.mSendOtp(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                otpLiveData.postValue(apiResponse!!.loading())
            }.subscribe({ result ->
                otpLiveData.postValue(apiResponse!!.success(result))
            }, { throwable: Throwable? ->
                otpLiveData.postValue(apiResponse!!.error(throwable!!))
            }
            )
    }

    fun forgotPwd() {
        val app = App.singleton
        if (TextUtils.isEmpty(emial.value)) {
            error.value = app!!.getString(R.string.enter_email)
            return
        } else if (!AppDelegate.isEmailValid(emial.value!!)) {
            error.value = app!!.getString(R.string.valid_email)
            return
        }

        val request = RequestModel()
        request.email = emial.value
        request.language = "en"

        subscription = userDataSource!!.forgotPassword(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                commonLiveData.postValue(commonApiResponse!!.loading())
            }.subscribe({ result ->
                commonLiveData.postValue(commonApiResponse!!.success(result))
            }, { throwable: Throwable? ->
                commonLiveData.postValue(commonApiResponse!!.error(throwable!!))
            }
            )
    }

    fun changePwd(mUserId:String?) {
        if(!isValidUserDataCP())
            return

        val request = RequestModel()
        request.oldPassword = mOldPassword.value
        request.newPassword = confirmPwd.value
        request.userId = mUserId
        request.language="en"

        subscription = userDataSource!!.mChangePassword(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                commonLiveData.postValue(commonApiResponse!!.loading())
            }.subscribe({ result ->
                commonLiveData.postValue(commonApiResponse!!.success(result))
            }, { throwable: Throwable? ->
                commonLiveData.postValue(commonApiResponse!!.error(throwable!!))
            }
            )
    }

    fun logoutApi( userId: String?) {
        val request = RequestModel()
        request.userId = userId
        request.language = "en"


    /*    subscription = userDataSource!!.logout(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                logoutLiveData.postValue(commonApiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    logoutLiveData.postValue(commonApiResponse!!.success(result))
                },
                { throwable ->
                    logoutLiveData.postValue(commonApiResponse!!.error(throwable))
                }
            )*/

    }





}
