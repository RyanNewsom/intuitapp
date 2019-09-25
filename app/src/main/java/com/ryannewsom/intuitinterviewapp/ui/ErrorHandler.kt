package com.ryannewsom.intuitinterviewapp.ui

import java.lang.Exception

interface ErrorHandler {
    fun showError(exception: Exception, retryHandler: (() -> Unit)?)
}