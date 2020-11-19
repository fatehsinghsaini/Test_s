package com.os.busservice.data.datasource

import com.os.busservice.data.retro.RestApi
import com.os.busservice.model.CommonResponse
import com.os.busservice.model.RequestModel
import com.os.busservice.model.busListResponse.SeatListRequest
import com.os.busservice.model.busListResponse.SeatListResponse
import com.os.busservice.model.couponCode.CouponListResponse
import com.os.busservice.model.loginResponse.LoginResponse
import io.reactivex.Observable


class UserDataSource(private var apiCallInterface: RestApi) {

    fun mLogin(loginRequest: RequestModel): Observable<LoginResponse> {
        return apiCallInterface.login(loginRequest)
    }

    fun mSignUp(loginRequest: RequestModel): Observable<LoginResponse> {
        return apiCallInterface.mSignup(loginRequest)
    }
    fun socialLogin(loginRequest: RequestModel): Observable<LoginResponse> {

       val request= if(loginRequest.fb_id.isNullOrEmpty()) apiCallInterface.mGoogleLogin(loginRequest)
       else
           apiCallInterface.mFacebokLogin(loginRequest)

        return request
    }

    fun mSendOtp(loginRequest: RequestModel): Observable<LoginResponse> {
        return apiCallInterface.mSendOtp(loginRequest)
    }

    fun mLogout(loginRequest: RequestModel): Observable<CommonResponse> {
        return apiCallInterface.mLogout(loginRequest)
    }

    fun forgotPassword(loginRequest: RequestModel): Observable<CommonResponse> {
        return apiCallInterface.mForgotPassword(loginRequest)
    }

    fun mChangePassword(loginRequest: RequestModel): Observable<CommonResponse> {
        return apiCallInterface.mChangePassword(loginRequest)
    }
    fun mBusSeatList(loginRequest: SeatListRequest): Observable<SeatListResponse> {
        return apiCallInterface.mBusSeatList(loginRequest)
    }
    fun mCouponList(loginRequest: RequestModel): Observable<CouponListResponse> {
        return apiCallInterface.mCouponList(loginRequest)
    }
    fun mGeneratePaytmChecksum(loginRequest: RequestModel): Observable<CouponListResponse> {
        return apiCallInterface.mGeneratePaytmChecksum(loginRequest)
    }
}