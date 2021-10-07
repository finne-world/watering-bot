package com.it_finne.watering.lib.aws

interface AwsParameterStore {
    fun getParameter(parameterName: String): String
}