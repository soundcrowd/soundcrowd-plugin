/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins

import java.io.BufferedReader
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WebRequests {

    class Response(val status : Int, val value : String)

    /**
     * Request an URL via get and return its content
     *
     * @param url the url to request via get
     * @return the contents of the specified text file
     */
    @Throws(IOException::class)
    fun get(url: String): Response {
        return request(createConnection(url))
    }

    fun post(url: String, data: String): Response {
        val con = createConnection(url)
        con.doOutput = true
        con.requestMethod = "POST"
        con.outputStream.write(data.toByteArray())
        return request(con)
    }

    fun request(url: String, method: String, headers: Map<String, String>? = null): Response {
        val con = createConnection(url)
        con.requestMethod = method
        if (headers != null) {
            for ((k,v) in headers) {
                con.setRequestProperty(k, v)
            }
        }
        return request(con)
    }

    @Throws(IOException::class)
    fun createConnection(url: String): HttpsURLConnection {
        val con = URL(url).openConnection() as? HttpsURLConnection ?: throw IOException()
        con.instanceFollowRedirects = false
        con.connectTimeout = 30 * 1000 // 30s
        con.readTimeout = 30 * 1000 // 30s

        return con
    }

    @Throws(HttpException::class)
    fun request(con: HttpsURLConnection): Response {
        if (con.responseCode < 400) {
            return Response(con.responseCode, con.inputStream.bufferedReader().use(BufferedReader::readText))
        } else {
            throw HttpException(con.responseCode, con.errorStream.bufferedReader().use(BufferedReader::readText))
        }
    }

    class HttpException(val code: Int, message: String?) : IOException(message)
}
