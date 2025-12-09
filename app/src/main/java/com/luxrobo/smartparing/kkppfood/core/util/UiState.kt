package com.luxrobo.smartparing.kkppfood.core.util

/**
 * 화면에서 공통으로 사용할 수 있는 간단한 UI 상태 베이스.
 * 각 화면별로 상속해서 써도 되고, 별도 data class를 만들어도 된다.
 */
open class UiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
