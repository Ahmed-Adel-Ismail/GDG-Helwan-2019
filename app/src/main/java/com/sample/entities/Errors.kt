package com.sample.entities

import java.lang.RuntimeException

class ValidationException(message: String) : RuntimeException(message)