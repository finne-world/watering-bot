package com.it_finne.watering.error

import java.lang.RuntimeException

class IllegalEnvironmentException(environmentValue: String) : RuntimeException(
    "invalid environment: ${environmentValue}. please choice production, staging or development."
)
