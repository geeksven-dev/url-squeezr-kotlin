package com.geeksven.squeezr.model.api

import org.hibernate.validator.constraints.URL

data class SqueezeCreateRequest(@field:URL val fullUrl: String, val customSlug: String?)
