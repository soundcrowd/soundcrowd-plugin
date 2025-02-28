/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins

import android.media.MediaDataSource
import android.net.Uri
import androidx.media3.common.MediaItem

interface UrlResolver {

    /**
     * Resolve media item url to the actual stream url, if necessary
     */
    @Throws(Exception::class)
    fun getMediaUri(mediaItem: MediaItem): Uri? = null

    /**
     * Resolve media item to a playable MediaDataSource, if METADATA_KEY_DATASOURCE
     * is set in the MediaItem
     */
    @Throws(Exception::class)
    fun getDataSource(mediaItem: MediaItem): MediaDataSource? = null
}