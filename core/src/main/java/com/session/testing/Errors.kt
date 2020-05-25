package com.session.testing

import java.lang.RuntimeException

class FailedToRequestException(message: String? = null) : RuntimeException(message)