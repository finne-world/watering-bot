package com.it_finne.watering.error

class MultipleErrorsInResponseException (
    errorDescription: String
) : RuntimeException(
    errorDescription
)
