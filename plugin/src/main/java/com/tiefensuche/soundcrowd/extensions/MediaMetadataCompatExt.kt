/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.extensions

/**
 * Extensions for MediaMetadataCompat
 */
object MediaMetadataCompatExt {

    const val METADATA_KEY_DOWNLOAD_URL = "DOWNLOAD_URL"
    const val METADATA_KEY_WAVEFORM_URL = "WAVEFORM_URL"
    const val METADATA_KEY_TYPE = "TYPE"
    const val METADATA_KEY_SOURCE = "SOURCE"

    // values for METADATA_KEY_TYPE
    enum class MediaType {
        MEDIA, COLLECTION, STREAM
    }
}