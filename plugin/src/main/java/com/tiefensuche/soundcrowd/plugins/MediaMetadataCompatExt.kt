/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins


/**
 * Extensions for MediaMetadataCompat
 */
object MediaMetadataCompatExt {

    const val METADATA_KEY_URL = "URL"
    const val METADATA_KEY_WAVEFORM_URL = "WAVEFORM_URL"
    const val METADATA_KEY_PLUGIN = "PLUGIN"
    const val METADATA_KEY_DATASOURCE = "DATASOURCE"
    const val METADATA_KEY_TYPE = "TYPE"

    // values for METADATA_KEY_TYPE
    enum class MediaType {
        MEDIA, COLLECTION, STREAM
    }

    const val COMMAND_LIKE = "COMMAND_LIKE"
    const val FROM_RECENT_SEARCH_QUERIES = "FROM_RECENT_SEARCH_QUERIES"
}