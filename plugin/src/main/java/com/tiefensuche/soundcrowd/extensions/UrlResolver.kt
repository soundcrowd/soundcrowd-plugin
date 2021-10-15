/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.extensions

import android.media.MediaDataSource
import android.support.v4.media.MediaMetadataCompat
import com.tiefensuche.soundcrowd.plugins.Callback

interface UrlResolver {

    /**
     * Resolve media item url to the actual stream url, in case it is necessary
     */
    @Throws(Exception::class)
    fun getMediaUrl(metadata: MediaMetadataCompat, callback: Callback<Pair<MediaMetadataCompat, MediaDataSource?>>) {
        callback.onResult(Pair(metadata, null))
    }
}