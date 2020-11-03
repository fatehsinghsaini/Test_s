package com.os.busservice.data.retro

import com.os.busservice.model.CommonResponse
import com.os.busservice.model.RequestModel
import com.os.busservice.model.busListResponse.SeatListRequest
import com.os.busservice.model.busListResponse.SeatListResponse
import com.os.busservice.model.loginResponse.LoginResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST


interface RestApi {

    @POST("login")
    fun login(@Body loginRequest: RequestModel): Observable<LoginResponse>

    @POST("signup")
    fun mSignup(@Body loginRequest: RequestModel): Observable<LoginResponse>

    @POST("generat_otp")
    fun mSendOtp(@Body loginRequest: RequestModel): Observable<LoginResponse>
    @POST("logout")
    fun mLogout(@Body loginRequest: RequestModel): Observable<CommonResponse>

    @POST("facebook_login")
    fun mFacebokLogin(@Body loginRequest: RequestModel): Observable<LoginResponse>
    @POST("google_login")
    fun mGoogleLogin(@Body loginRequest: RequestModel): Observable<LoginResponse>

    @POST("generat_otp")
    fun mForgotPassword(@Body loginRequest: RequestModel): Observable<CommonResponse>

    @POST("generat_otp")
    fun mChangePassword(@Body loginRequest: RequestModel): Observable<CommonResponse>

    @POST("bus_route_listing")
    fun mBusSeatList(@Body loginRequest: SeatListRequest): Observable<SeatListResponse>

   /* @Multipart
    @POST("update-profile")
    fun mUpdateProfile(@Part("data") loginRequest: okhttp3.RequestBody?, @Part mImage:MultipartBody.Part?): Observable<UpdateProfileResponse>*/
}
