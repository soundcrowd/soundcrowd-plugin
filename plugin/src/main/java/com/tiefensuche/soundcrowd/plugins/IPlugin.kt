/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins

import android.content.Context
import com.tiefensuche.soundcrowd.extensions.UrlResolver
import org.json.JSONArray

interface IPlugin : UrlResolver {

    /**
     * Initialization function for the plugin
     *
     * @param context context of the main activity
     */
    fun init(context: Context)

    /**
     * The name of the plugin, it will be displayed in the UI
     *
     * @return plugin name
     */
    fun name(): String

    /**
     * List of media categories of the plugin, each category will add a new entry in the addons
     * section in the UI
     *
     * @return list of media categories
     */
    fun mediaCategories(): List<String>

    /**
     * Request to get media items for one of the supported [.mediaCategories].
     * At the moment the plugin keeps the state what items have been returned, so a second
     * call should return the next items and if there are no more it should return empty results.
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param callback callback with the results
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, callback: Callback<JSONArray>)

    /**
     * Request to get media items for one of the supported [.mediaCategories] at the given path.
     * At the moment the plugin keeps the state what items have been returned, so a second
     * call should return the next items and if there are no more it should return empty results.
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param path path in the category
     * @param callback callback with the results
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, path: String, callback: Callback<JSONArray>)

    /**
     * Request to query media items in the given path for the given search string.
     * A second call with the same query should return the next items, same as in [.getMediaItems]
     *
     * @param mediaCategory one of [.mediaCategories]
     * @param path path in the category
     * @param query search string
     * @param callback callback with the results
     * @throws Exception any type of exceptions that can occur for the request in the plugin
     */
    @Throws(Exception::class)
    fun getMediaItems(mediaCategory: String, path: String, query: String, callback: Callback<JSONArray>)
}
