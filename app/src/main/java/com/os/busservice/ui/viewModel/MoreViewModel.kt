package com.os.busservice.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os.busservice.data.ApiResponse
import com.os.busservice.data.datasource.UserDataSource
import com.os.busservice.data.retro.RestApiFactory
import com.os.busservice.model.CommonResponse
import com.os.busservice.model.RequestModel
import com.os.busservice.model.couponCode.CouponListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class MoreViewModel : ViewModel() {

    var userDataSource: UserDataSource? = null

    var firstName = MutableLiveData<String>()
    var lastName = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var mobile = MutableLiveData<String>()
    var error = MutableLiveData<String>()
    var mCouponListLiveData = MutableLiveData<ApiResponse<CouponListResponse>>()
/*    var staticPageLiveData = MutableLiveData<ApiResponse<StaticPageResponse>>()
    var notificationLiveData = MutableLiveData<ApiResponse<NotificationResponse>>()
    var deleteNotificationLiveData = MutableLiveData<ApiResponse<NotificationResponse>>()
    var updateProfileLiveData = MutableLiveData<ApiResponse<UpdateProfileResponse>>()*/


    private var restApiFactory: RestApiFactory? = null
   var apiResponse: ApiResponse<CouponListResponse>? = null
    /*   var mNotificationApiResponse: ApiResponse<NotificationResponse>? = null
      var mUpdateProfileApiResponse: ApiResponse<UpdateProfileResponse>? = null*/

    private var subscription: Disposable? = null
    init {
        restApiFactory = RestApiFactory
        userDataSource = UserDataSource(restApiFactory!!.create())
       apiResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)
        /*   mNotificationApiResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)
          mUpdateProfileApiResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)*/

    }


   /* fun getNotificationList(userId: String?) {
        val request = RequestModel()
        request.language = "en"
        request.userId = userId

        subscription = userDataSource!!.mNotificationList(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                notificationLiveData.postValue(mNotificationApiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    notificationLiveData.postValue(mNotificationApiResponse!!.success(result))
                },
                { throwable ->
                    notificationLiveData.postValue(mNotificationApiResponse!!.error(throwable))
                }
            )

    }

    fun mDeleteNotification(notificationId: String?, id: String?) {
        val request = RequestModel()
        request.language = "en"
        request.userId = id
        request.notificationId = notificationId

        subscription = userDataSource!!.mNotificationDelete(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                deleteNotificationLiveData.postValue(mNotificationApiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    deleteNotificationLiveData.postValue(mNotificationApiResponse!!.success(result))
                },
                { throwable ->
                    deleteNotificationLiveData.postValue(mNotificationApiResponse!!.error(throwable))
                }
            )

    }

    fun getSlugApi(pageSlug: String?) {
        val request = CommonRequest()
        request.language = "en"
        request.slug = pageSlug

        subscription = userDataSource!!.mStaticPage(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                staticPageLiveData.postValue(apiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    staticPageLiveData.postValue(apiResponse!!.success(result))
                },
                { throwable ->
                    staticPageLiveData.postValue(apiResponse!!.error(throwable))
                }
            )

    }

    fun mUpdateEditProfile(userId: String?,firstName: String?,lastName: String?,file:File?) {

        val request = JSONObject()
        request.put("first_name", firstName)
        request.put("last_name", lastName)
        request.put("language", "en")
        request.put("user_id",userId)
        val requestBody =
            request.toString().toRequestBody("applications/json".toMediaType())

        val partImage = UtilityMethods.createPart(file, "photo")

        subscription = userDataSource!!.mUpdateProfile(requestBody,partImage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                updateProfileLiveData.postValue(mUpdateProfileApiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    updateProfileLiveData.postValue(mUpdateProfileApiResponse!!.success(result))
                },
                { throwable ->
                    updateProfileLiveData.postValue(mUpdateProfileApiResponse!!.error(throwable))
                }
            )

    }*/


    fun mGetCouponList(userId: String?) {
        val request = RequestModel()
        request.language = "en"
        request.user_id = userId


        subscription = userDataSource!!.mCouponList(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                mCouponListLiveData.postValue(apiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    mCouponListLiveData.postValue(apiResponse!!.success(result))
                },
                { throwable ->
                    mCouponListLiveData.postValue(apiResponse!!.error(throwable))
                }
            )

    }


}
