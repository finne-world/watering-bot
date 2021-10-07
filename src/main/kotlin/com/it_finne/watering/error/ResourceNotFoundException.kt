package com.it_finne.watering.error

class ResourceNotFoundException(
    errorDescription: String
): RuntimeException(
    errorDescription
)