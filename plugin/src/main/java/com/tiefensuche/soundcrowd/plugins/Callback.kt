/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins

/**
 * Callback interface for soundcrowd addons
 */
interface Callback<T> {

    /**
     * Callback method when result is available
     */
    fun onResult(result: T)
}