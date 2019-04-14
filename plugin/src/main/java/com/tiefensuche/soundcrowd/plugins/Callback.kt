/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.plugins

interface Callback<T> {
    fun onResult(result: T)
}