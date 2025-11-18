package dev.corazza.mytasks.repository

data class ResponseDto<T>(
  val value: T? = null,
  val error: Boolean = false
)
