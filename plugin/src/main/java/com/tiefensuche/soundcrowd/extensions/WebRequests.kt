/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.extensions

import java.io.BufferedReader
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection

object WebRequests {

    private const val USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:43.0) Gecko/20100101 Firefox/43.0"
    @get:Synchronized
    @set:Synchronized
    var cookies = ""

    /**
     * Download (via HTTP) the text file located at the supplied URL, and return its contents.
     * Primarily intended for downloading web pages.
     *
     * @param siteUrl the URL of the text file to download
     * @return the contents of the specified text file
     */
    @Throws(IOException::class)
    fun get(siteUrl: String): String {
        return request(createConnection(siteUrl))
    }

    fun post(siteUrl: String, content: String): String {
        val con = createConnection(siteUrl)
        con.doOutput = true
        con.requestMethod = "POST"
        con.outputStream.write(content.toByteArray())
        return request(con)
    }

    @Throws(IOException::class)
    fun createConnection(url: String): HttpsURLConnection {
        val con = URL(url).openConnection() as? HttpsURLConnection ?: throw IOException()
        con.connectTimeout = 30 * 1000 // 30s
        con.readTimeout = 30 * 1000 // 30s
        con.setRequestProperty("User-Agent", USER_AGENT)

        if (cookies.isNotEmpty()) {
            con.setRequestProperty("Cookie", cookies)
        }

        return con
    }

    /**
     * Common functionality between download(String url) and download(String url, String language)
     */
    @Throws(HttpException::class)
    fun request(con: HttpsURLConnection): String {
        if (con.responseCode < 400) {
            return con.inputStream.bufferedReader().use(BufferedReader::readText)
        } else {
            throw HttpException(con.responseCode, con.errorStream.bufferedReader().use(BufferedReader::readText))
        }
    }

    class HttpException(val code: Int, message: String?) : IOException(message)
}
