/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.extensions

import com.tiefensuche.soundcrowd.plugins.Callback

interface UrlResolver {

    /**
     * Resolve media item url to the actual stream url, in case it is necessary
     */
    @Throws(Exception::class)
    fun getMediaUrl(url: String, callback: Callback<String>)
}