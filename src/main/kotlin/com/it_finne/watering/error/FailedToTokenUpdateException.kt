package com.it_finne.watering.error

class FailedToTokenUpdateException(
    errorDescription: String
) : RuntimeException(
    errorDescription
)