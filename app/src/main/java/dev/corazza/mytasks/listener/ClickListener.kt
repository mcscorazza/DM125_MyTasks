package dev.corazza.mytasks.listener

import dev.corazza.mytasks.entity.Task

interface ClickListener {
    fun onComplete(id: Long)

    fun onClick(task: Task)
}