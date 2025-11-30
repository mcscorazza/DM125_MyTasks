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

  fun update(task: Task): LiveData<ResponseDto<Task>> {
    val taskLiveData = MutableLiveData<ResponseDto<Task>>()
    taskRepository.update(task.id!!, task).enqueue(ServiceCallback(taskLiveData))
    return taskLiveData
  }
  fun list() : LiveData<ResponseDto<List<Task>>> {
    val tasksLiveData = MutableLiveData<ResponseDto<List<Task>>>()
    taskRepository.list().enqueue(ServiceCallback<List<Task>>(tasksLiveData))
    return tasksLiveData
  }

  fun read(id: Long): LiveData<ResponseDto<Task>> {
    val taskLiveData = MutableLiveData<ResponseDto<Task>>()
    taskRepository.read(id).enqueue(ServiceCallback(taskLiveData))
    return taskLiveData
  }

  fun delete(id: Long) : LiveData<ResponseDto<Void>> {
    val liveData = MutableLiveData<ResponseDto<Void>>()
    taskRepository.delete(id).enqueue(ServiceCallback(liveData))
    return liveData
  }

  fun complete(id:Long) : LiveData<ResponseDto<Task>> {
    val taskLiveData = MutableLiveData<ResponseDto<Task>>()
    taskRepository.complete(id).enqueue(ServiceCallback(taskLiveData))
    return taskLiveData
  }
}