/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins

import android.graphics.Bitmap
import android.support.v4.media.MediaMetadataCompat
import androidx.preference.Preference
import com.tiefensuche.soundcrowd.extensions.UrlResolver

/**
 * Plugin interface for soundcrowd addons
 */
interface IPlugin : UrlResolver {

    /**
     * The name of the plugin, it will be displayed in the UI
     *
     * @return plugin name
     */
    fun name(): String = "Unnamed"

    /**
     * List of media categories of the plugin, each category will add a new entry in the addons
     * section in the UI
     *
     * @return list of media categories
     */
    fun mediaCategories(): List<String> = emptyList()

    /**
     * JSONArray that consists of preferences encoded in JSONObjects. A preference in JSONObject
     * consists of key/value pairs of following keys:
     *
     * - key: the key under which the option is stored
     * - name: the display name of the option
     * - description: a short description of the option
     *
     * @return JSONArray with encoded preferences
     */
    fun preferences(): List<Preference> = emptyList()

    /**
     * Request to get media items for one of the supported [.mediaCategories].
     * At the moment the plugin keeps the state what items have been returned, so a second
     * call should return the next items and if there are no more it should return empty results.
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param callback callback with the results
     * @param refresh reset state and perform a new query
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, callback: Callback<List<MediaMetadataCompat>>, refresh: Boolean = false) {
        callback.onResult(emptyList())
    }

    /**
     * Request to get media items for one of the supported [.mediaCategories] at the given path.
     * At the moment the plugin keeps the state what items have been returned, so a second
     * call should return the next items and if there are no more it should return empty results.
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param path path in the category
     * @param callback callback with the results
     * @param refresh reset state and perform a new query
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, path: String, callback: Callback<List<MediaMetadataCompat>>, refresh: Boolean = false) {
        callback.onResult(emptyList())
    }

    /**
     * Request to query media items in the given path for the given search string.
     * A second call with the same query should return the next items, same as in [.getMediaItems]
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param path path in the category
     * @param query search string
     * @param callback callback with the results
     * @param refresh reset state and perform a new query
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, path: String, query: String, callback: Callback<List<MediaMetadataCompat>>, refresh: Boolean = false) {
        callback.onResult(emptyList())
    }

    /**
     * Request to toggle favorite status for a given id.
     *
     * @param id media id
     * @param callback callback with status
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun favorite(id: String, callback: Callback<Boolean>) {
        callback.onResult(false)
    }

    /**
     * Returns a bitmap that represents the plugin
     *
     * @return bitmap with plugin icon
     */
    fun getIcon(): Bitmap

    fun callbacks(): Map<String, (callback: String) -> Unit> = emptyMap()
}
