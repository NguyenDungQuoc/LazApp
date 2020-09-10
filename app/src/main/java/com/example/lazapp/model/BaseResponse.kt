package com.example.lazapp.model

class BaseResponse<T>(
    var status: StatusProduct,
    var result: T? = null
)