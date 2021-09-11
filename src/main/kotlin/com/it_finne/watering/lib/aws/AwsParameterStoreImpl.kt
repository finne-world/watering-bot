package com.it_finne.watering.lib.aws

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement
import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagementClientBuilder
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest
import com.it_finne.watering.lib.aws.AwsParameterStore


class AwsParameterStoreImpl : AwsParameterStore {
    private val ssm: AWSSimpleSystemsManagement = AWSSimpleSystemsManagementClientBuilder.defaultClient()

    override fun getParameter(parameterName: String): String {
        val request: GetParameterRequest = GetParameterRequest()
        request.name = parameterName
        request.withDecryption = true
        return ssm.getParameter(request).parameter.value
    }
}