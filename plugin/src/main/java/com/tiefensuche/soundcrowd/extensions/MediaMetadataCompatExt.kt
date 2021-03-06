/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.extensions

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.RatingCompat
import org.json.JSONException
import org.json.JSONObject

/**
 * Extensions for MediaMetadataCompat
 */
object MediaMetadataCompatExt {

    const val METADATA_KEY_URL = "URL"
    const val METADATA_KEY_FAVORITE = "FAVORITE"
    const val METADATA_KEY_DOWNLOAD_URL = "DOWNLOAD_URL"
    const val METADATA_KEY_WAVEFORM_URL = "WAVEFORM_URL"
    const val METADATA_KEY_TYPE = "TYPE"
    const val METADATA_KEY_SOURCE = "SOURCE"

    // values for METADATA_KEY_TYPE
    enum class MediaType {
        MEDIA, COLLECTION, STREAM
    }

    @Throws(JSONException::class)
    fun fromJson(json: JSONObject): MediaMetadataCompat {
        val builder = MediaMetadataCompat.Builder()
        val keys = json.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            when (val value = json.get(key)) {
                is String -> builder.putString(key, value)
                is Long -> builder.putLong(key, value)
                is Int -> builder.putLong(key, value.toLong())
                is Boolean -> builder.putRating(key, RatingCompat.newHeartRating(value))
            }
        }
        return builder.build()
    }

    fun toJSON(metadata: MediaMetadataCompat): JSONObject {
        val json = JSONObject()
        metadata.keySet().forEach { key ->
            json.put(key, metadata.bundle.get(key))
        }
        return json
    }
}