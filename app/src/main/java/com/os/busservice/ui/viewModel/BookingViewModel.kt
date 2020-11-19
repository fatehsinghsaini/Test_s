package com.os.busservice.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os.busservice.data.ApiResponse
import com.os.busservice.data.datasource.UserDataSource
import com.os.busservice.data.retro.RestApiFactory
import com.os.busservice.model.RequestModel
import com.os.busservice.model.couponCode.CouponListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BookingViewModel : ViewModel() {

    var userDataSource: UserDataSource? = null

    var error = MutableLiveData<String>()
    var mPaytmChecksumLiveData = MutableLiveData<ApiResponse<CouponListResponse>>()

    private var restApiFactory: RestApiFactory? = null
   var apiResponse: ApiResponse<CouponListResponse>? = null

    private var subscription: Disposable? = null
    init {
        restApiFactory = RestApiFactory
        userDataSource = UserDataSource(restApiFactory!!.create())
       apiResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)

    }


    fun mGeneratePaytmChecksum(request: RequestModel) {

        subscription = userDataSource!!.mGeneratePaytmChecksum(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                mPaytmChecksumLiveData.postValue(apiResponse!!.loading())
            }
            .subscribe(
                { result ->
                    mPaytmChecksumLiveData.postValue(apiResponse!!.success(result))
                },
                { throwable ->
                    mPaytmChecksumLiveData.postValue(apiResponse!!.error(throwable))
                }
            )

    }


}
