package com.ryannewsom.intuitinterviewapp.data.http

import java.lang.Exception

class HttpRequestFailedException(
    val statusCode: Int
) : Exception()