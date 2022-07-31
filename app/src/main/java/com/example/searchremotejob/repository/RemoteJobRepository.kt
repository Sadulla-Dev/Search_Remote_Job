package com.example.searchremotejob.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.searchremotejob.api.RetrofitInstance
import com.example.searchremotejob.db.RemoteJobDatabase
import com.example.searchremotejob.model.JobToSave
import com.example.searchremotejob.model.RemoteJob
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteJobRepository(private val db: RemoteJobDatabase) {

    private val remoteJobService = RetrofitInstance.api
    private val remoteJobResponseLiveData: MutableLiveData<RemoteJob> = MutableLiveData()
    private val searchRemoteJobLiveData: MutableLiveData<RemoteJob> = MutableLiveData()


    init {

        getRemoteJobResponse()
    }

    private fun getRemoteJobResponse() {

        remoteJobService.getRemoteJob().enqueue(
            object : Callback<RemoteJob> {
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    if (response.body() != null) {
                        remoteJobResponseLiveData.postValue(response.body())
                        Log.d("home_sadulla", response.body()!!.jobs.toString())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
//                    remoteJobResponseLiveData.postValue(null)
                    Log.d("error ibm", t.message.toString())
                }
            })
    }


    fun searchRemoteJob(query: String?) {
        remoteJobService.searchRemoteJob(query).enqueue(
            object : Callback<RemoteJob> {
                override fun onResponse(call: Call<RemoteJob>, response: Response<RemoteJob>) {
                    if (response.body() != null) {
                        searchRemoteJobLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<RemoteJob>, t: Throwable) {
//                    searchRemoteJobLiveData.postValue(null)
                    Log.d("error ibm", t.message.toString())
                }

            }
        )

    }


    fun getRemoteJobResponseLiveData(): LiveData<RemoteJob> {
        return remoteJobResponseLiveData
    }

    fun getSearchJobResponseLiveData(): LiveData<RemoteJob> {
        return searchRemoteJobLiveData
    }


    suspend fun insertJob(job: JobToSave) = db.getRemoteJobDao().insertJob(job)
    suspend fun deleteJob(job: JobToSave) = db.getRemoteJobDao().deleteJob(job)
    fun getAllJobs() = db.getRemoteJobDao().getAllJob()

}