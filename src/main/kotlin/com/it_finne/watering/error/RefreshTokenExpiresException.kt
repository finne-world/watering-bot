package com.it_finne.watering.error

class RefreshTokenExpiresException (
    errorDescription: String
) : RuntimeException(
    errorDescription
)