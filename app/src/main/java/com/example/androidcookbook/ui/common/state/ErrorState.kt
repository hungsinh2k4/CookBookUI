package com.example.androidcookbook.ui.common.state

import com.example.androidcookbook.R

/**
 * Error state holding values for error ui
 */
data class ErrorState(
    val hasError: Boolean = false,
    val errorMessageStringResource: Int = R.string.empty_string
)