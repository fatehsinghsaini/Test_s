package com.os.busservice.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os.busservice.data.ApiResponse
import com.os.busservice.data.datasource.UserDataSource
import com.os.busservice.data.retro.RestApiFactory
import com.os.busservice.model.RequestModel
import com.os.busservice.model.busListResponse.SeatListRequest
import com.os.busservice.model.busListResponse.SeatListResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

class HomeViewModel : ViewModel() {

    var userDataSource: UserDataSource? = null

    var firstName = MutableLiveData<String>()
    var lastName = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var mobile = MutableLiveData<String>()
    var error = MutableLiveData<String>()
    private var restApiFactory: RestApiFactory? = null
    private var subscription: Disposable? = null
    var mBusSeatLiveData = MutableLiveData<ApiResponse<SeatListResponse>>()
    var mSeatListResponse: ApiResponse<SeatListResponse>? = null
    init {
        restApiFactory = RestApiFactory
        userDataSource = UserDataSource(restApiFactory!!.create())
        mSeatListResponse = ApiResponse(ApiResponse.Status.LOADING, null, null)


    }


   fun mBusSeatListResponse(request: SeatListRequest) {

        subscription = userDataSource!!.mBusSeatList(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d ->
                mBusSeatLiveData.postValue(mSeatListResponse!!.loading())
            }
            .subscribe(
                { result ->
                    mBusSeatLiveData.postValue(mSeatListResponse!!.success(result))
                },
                { throwable ->
                    mBusSeatLiveData.postValue(mSeatListResponse!!.error(throwable))
                }
            )

    }



}
