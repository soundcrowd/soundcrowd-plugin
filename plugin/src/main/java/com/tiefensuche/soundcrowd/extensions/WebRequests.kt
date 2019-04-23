/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.extensions

import java.io.BufferedReader
import java.io.IOException
import java.net.URL
import java.net.UnknownHostException
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
        val con = URL(siteUrl).openConnection() as HttpsURLConnection
        return request(con)
    }

    fun post(siteUrl: String, content: String): String {
        val con = URL(siteUrl).openConnection() as HttpsURLConnection
        con.doOutput = true
        con.requestMethod = "POST"
        con.outputStream.write(content.toByteArray())
        return request(con)
    }

    /**
     * Common functionality between download(String url) and download(String url, String language)
     */
    @Throws(IOException::class)
    fun request(con: HttpsURLConnection): String {
        val response: String
        try {
            con.connectTimeout = 30 * 1000 // 30s
            con.readTimeout = 30 * 1000 // 30s
            con.setRequestProperty("User-Agent", USER_AGENT)

            if (cookies.isNotEmpty()) {
                con.setRequestProperty("Cookie", cookies)
            }

            response = con.inputStream.bufferedReader().use(BufferedReader::readText)

        } catch (e: UnknownHostException) { //thrown when there's no internet connection
            throw IOException("unknown host or no network", e)
        } catch (e: Exception) {
            /*
            * HTTP 429 == Too Many Request
            * Receive from Youtube.com = ReCaptcha challenge request
            * See : https://github.com/rg3/youtube-dl/issues/5138
            */
            if (con.responseCode == 429) {
                throw IOException("reCaptcha Challenge requested")
            }

            throw IOException(con.responseCode.toString() + " " + con.responseMessage, e)
        }

        return response
    }
}
