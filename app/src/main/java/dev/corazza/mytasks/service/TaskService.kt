package dev.corazza.mytasks.service

import dev.corazza.mytasks.entity.Task

class TaskService {

    private val taskRepository = RetrofitService().getTaskRepository()

    fun create(task: Task) {
        taskRepository.create(task)
    }
}