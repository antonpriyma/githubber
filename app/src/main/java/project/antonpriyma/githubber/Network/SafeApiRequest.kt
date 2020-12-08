

package com.antonpriyma.githubber.Network

import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun <T: Any> apiRequest(call: suspend() -> Response<T>) : T {


            val response = call.invoke()

                if (response.body() != null) {

                    return response.body()!!

                } else {

                    throw ApiException(response.code().toString())
                }

    }

    suspend fun <T: Any> apiResponseCode(call: suspend() -> Response<T>) : Int {

        val response = call.invoke()

        return response.code()
    }

}

class ApiException (message : String): Exception(message)