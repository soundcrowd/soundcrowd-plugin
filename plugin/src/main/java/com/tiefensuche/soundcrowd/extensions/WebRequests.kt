/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.extensions

import java.io.BufferedReader
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WebRequests {

    @get:Synchronized
    @set:Synchronized
    var cookies = ""

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

    fun post(siteUrl: String, content: String): Response {
        val con = createConnection(siteUrl)
        con.doOutput = true
        con.requestMethod = "POST"
        con.outputStream.write(content.toByteArray())
        return request(con)
    }

    fun request(siteUrl: String, method: String): Response {
        val con = createConnection(siteUrl)
        con.requestMethod = method
        return request(con)
    }

    @Throws(IOException::class)
    fun createConnection(url: String): HttpsURLConnection {
        val con = URL(url).openConnection() as? HttpsURLConnection ?: throw IOException()
        con.connectTimeout = 30 * 1000 // 30s
        con.readTimeout = 30 * 1000 // 30s

        if (cookies.isNotEmpty()) {
            con.setRequestProperty("Cookie", cookies)
        }

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
