package com.example.searchremotejob.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.searchremotejob.model.JobToSave

@Dao
interface RemoteJobDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJob(job: JobToSave): Long

    @Query("SELECT * FROM job ORDER BY id DESC")
    fun getAllJob(): LiveData<List<JobToSave>>

    @Delete
    suspend fun deleteJob(job: JobToSave)
}