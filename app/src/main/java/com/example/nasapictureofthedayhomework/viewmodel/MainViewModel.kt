package com.example.nasapictureofthedayhomework.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasapictureofthedayhomework.BuildConfig
import com.example.nasapictureofthedayhomework.network.NasaApiService
import com.example.nasapictureofthedayhomework.network.NasaPhoto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MainViewModel : ViewModel() {
    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean>
        get() = _progressBar

    private val _todayPhotoButton = MutableLiveData<Boolean>()
    val todayPhotoButton: LiveData<Boolean>
        get() = _todayPhotoButton

    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private val _copyright = MutableLiveData<String>()
    val copyright: LiveData<String>
        get() = _copyright

    private val _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private val _description = MutableLiveData<String>()
    val description: LiveData<String>
        get() = _description

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String>
        get() = _photo

    private val _mediaType = MutableLiveData<String>()
    val mediaType: LiveData<String>
        get() = _mediaType

    private val yesterdayDate = LocalDate.now().minusDays(1).toString()
    private val dayBYesterdayDate = LocalDate.now().minusDays(2).toString()

    init {
        _status.value = "Photo is loading..."
        _progressBar.value = true
        getNasaPhoto()
    }

    fun getNasaPhoto() {

        val mCallMethodVar = NasaApiService.returnToVmFunction().getPhoto()

        mCallMethodVar.enqueue(object : Callback<NasaPhoto> {

            override fun onResponse(call: Call<NasaPhoto>, response: Response<NasaPhoto>) {

                if (!response.isSuccessful) {
                    _status.value = "ERROR: ${response.code()}"
                }

                _status.value = "Success"
                _progressBar.value = false
                _todayPhotoButton.value = false

                val body = response.body()
                _status.value = "Today's photo: ${body?.title}"
                _copyright.value = body?.copyright
                _date.value = body?.date
                _title.value = body?.title
                _description.value = body?.explanation
                _mediaType.value = body?.mediaType
                _photo.value = body?.url
            }

            override fun onFailure(call: Call<NasaPhoto>, t: Throwable) {
                _status.value = "ERROR: ${t.message}"
            }

        })
    }

    fun getYesterdayPhoto() {

        val mCallMethodVar = NasaApiService.returnToVmFunction().getPhotoByDate(
            yesterdayDate,
            BuildConfig.NASA_API_KEY
        )

        mCallMethodVar.enqueue(object : Callback<NasaPhoto> {

            override fun onResponse(call: Call<NasaPhoto>, response: Response<NasaPhoto>) {

                if (!response.isSuccessful) {
                    _status.value = "ERROR: ${response.code()}"
                }

                _status.value = "Success"
                _progressBar.value = false
                _todayPhotoButton.value = true

                val body = response.body()
                _status.value = "Yesterday's photo: ${body?.title}"
                _copyright.value = body?.copyright
                _date.value = body?.date
                _title.value = body?.title
                _description.value = body?.explanation
                _mediaType.value = body?.mediaType
                _photo.value = body?.url
            }

            override fun onFailure(call: Call<NasaPhoto>, t: Throwable) {
                _status.value = "ERROR: ${t.message}"
            }

        })
    }

    fun getDayBYesterdayPhoto() {

        val mCallMethodVar = NasaApiService.returnToVmFunction().getPhotoByDate(
            dayBYesterdayDate,
            BuildConfig.NASA_API_KEY
        )

        mCallMethodVar.enqueue(object : Callback<NasaPhoto> {

            override fun onResponse(call: Call<NasaPhoto>, response: Response<NasaPhoto>) {

                if (!response.isSuccessful) {
                    _status.value = "ERROR: ${response.code()}"
                }

                _status.value = "Success"
                _progressBar.value = false
                _todayPhotoButton.value = true

                val body = response.body()
                _status.value = "Day before yesterday's photo: ${body?.title}"
                _copyright.value = body?.copyright
                _date.value = body?.date
                _title.value = body?.title
                _description.value = body?.explanation
                _mediaType.value = body?.mediaType
                _photo.value = body?.url
            }

            override fun onFailure(call: Call<NasaPhoto>, t: Throwable) {
                _status.value = "ERROR: ${t.message}"
            }

        })
    }
}