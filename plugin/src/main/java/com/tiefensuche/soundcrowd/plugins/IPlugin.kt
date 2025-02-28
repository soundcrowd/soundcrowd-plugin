/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins

import android.graphics.Bitmap
import androidx.media3.common.MediaItem
import androidx.preference.Preference

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
     * List of media categories of the plugin, each category will add a new entry in the plugins
     * section in the UI
     *
     * @return list of media categories
     */
    fun mediaCategories(): List<String> = emptyList()

    /**
     * List of media search categories of the plugin, each category can be toggled in the search
     * section of the UI
     *
     * @return list of search categories
     */
    fun searchCategories(): List<String> = emptyList()

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
     * @param refresh reset state and perform a new query
     * @return List of media item results
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, refresh: Boolean = false): List<MediaItem> {
        return emptyList()
    }

    /**
     * Request to get media items for one of the supported [.mediaCategories] at the given path.
     * At the moment the plugin keeps the state what items have been returned, so a second
     * call should return the next items and if there are no more it should return empty results.
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param path path in the category
     * @param refresh reset state and perform a new query
     * @return List of media item results
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, path: String, refresh: Boolean = false): List<MediaItem> {
        return emptyList()
    }

    /**
     * Request to query media items in the given path for the given search string.
     * A second call with the same query should return the next items, same as in [.getMediaItems]
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param path path in the category
     * @param query search string
     * @param refresh reset state and perform a new query
     * @return List of media item results
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, path: String, query: String, type: String, refresh: Boolean = false): List<MediaItem> {
        return emptyList()
    }

    /**
     * Request to toggle favorite status for a given id.
     *
     * @param id media id
     * @return true if success, false otherwise
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun favorite(id: String): Boolean {
        return false
    }


    /**
     * Request search suggestions for a given category and query string
     *
     * @param category search category
     * @param query search query
     * @return List of search suggestions
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getSuggestions(category: String, query: String): List<String> {
        return emptyList()
    }

    /**
     * Returns a bitmap that represents the plugin
     *
     * @return bitmap with plugin icon
     */
    fun getIcon(): Bitmap

    /**
     * List of registered callbacks that are handled by the plugin
     *
     * @return Map of callback name to callback function
     */
    fun callbacks(): Map<String, (callback: String) -> Unit> = emptyMap()
}
