package com.it_finne.watering.error

class ApiErrorManager {
    private val errorMap: Map<String, String> = mapOf(
            "1001" to "1001:No parameters specified.",
            "1002" to "1002:Illegal parameter specified",
            "1003" to "1003:A non-existent guild ID has been specified.(INIT has not been executed.)",
            "1004" to "1004:The configuration already exists."
    )

    fun getErrorResponseByErrorCode(errorCode: String): String {
        return errorMap[errorCode] ?: return "An unknown error has occurred."
    }
}

