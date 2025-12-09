package com.luxrobo.smartparing.kkppfood.core.util

/**
 * 네트워크 / 로컬 데이터 처리 결과를 표현하기 위한 공통 Result 래퍼.
 */
sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()

    data class Error(
        val throwable: Throwable? = null,
        val message: String? = throwable?.localizedMessage
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()

    /**
     * 성공 여부 편의 프로퍼티
     */
    val isSuccess: Boolean get() = this is Success<T>

    /**
     * 실패 여부 편의 프로퍼티
     */
    val isError: Boolean get() = this is Error
}
