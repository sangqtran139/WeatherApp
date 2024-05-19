package com.sangtq.model.basenetwork

class ApiException(val code: String? = null, message: String? = null, localMessage: String? = null) : RuntimeException(message)
