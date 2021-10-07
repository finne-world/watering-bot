package com.it_finne.watering.error

class UpdateFailedException(
    errorDescription: String
): RuntimeException(
    errorDescription
)
