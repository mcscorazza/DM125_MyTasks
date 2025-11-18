package dev.corazza.mytasks.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.corazza.mytasks.entity.Task
import dev.corazza.mytasks.repository.ResponseDto

class TaskService : ViewModel() {

  private val taskRepository = RetrofitService().getTaskRepository()

  fun create(task: Task) : LiveData<ResponseDto<Task>> {
    val taskLiveData = MutableLiveData<ResponseDto<Task>>()
    taskRepository.create(task).enqueue(ServiceCallback<Task>(taskLiveData))
    return taskLiveData
  }

  fun list() : LiveData<ResponseDto<List<Task>>> {
    val tasksLiveData = MutableLiveData<ResponseDto<List<Task>>>()
    taskRepository.list().enqueue(ServiceCallback<List<Task>>(tasksLiveData))
    return tasksLiveData
  }
}