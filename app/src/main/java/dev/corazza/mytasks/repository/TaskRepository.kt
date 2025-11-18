package dev.corazza.mytasks.repository

import dev.corazza.mytasks.entity.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskRepository {
    @POST("/tasks")
    fun create(@Body task: Task) : Call<Task>
}