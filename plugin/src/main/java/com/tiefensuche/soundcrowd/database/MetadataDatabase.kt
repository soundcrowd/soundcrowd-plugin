/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.media.MediaMetadataCompat
import com.tiefensuche.soundcrowd.extensions.MediaMetadataCompatExt

/**
 * Database that stores media items, metadata, and [CuePoint] items
 *
 * Created by tiefensuche on 23.09.16.
 */
open class MetadataDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "SoundCrowd"
        private const val DATABASE_VERSION = 2

        const val ARTIST = "artist"
        const val TITLE = "title"
        const val ID = "id"
        const val DURATION = "duration"
        const val SOURCE = "source"
        const val DOWNLOAD = "download"
        const val ALBUM_ART_URL = "album_art_url"
        const val WAVEFORM_URL = "waveform_url"

        const val DATABASE_MEDIA_ITEMS_NAME = "MediaItems"

        private const val DATABASE_MEDIA_ITEMS_CREATE = "create table if not exists $DATABASE_MEDIA_ITEMS_NAME ($ID text primary key, $ARTIST text not null, $TITLE text not null, $DURATION long not null, $SOURCE text not null unique, $DOWNLOAD text unique, $ALBUM_ART_URL text not null, $WAVEFORM_URL text);"
    }

    init {
        writableDatabase.execSQL(DATABASE_MEDIA_ITEMS_CREATE)
    }

    fun addMediaItem(item: MediaMetadataCompat) {
        try {
            val values = ContentValues()
            val mediaId = item.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
            values.put(ID, mediaId.substring(mediaId.indexOf('|') + 1))
            values.put(SOURCE, item.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI))
            values.put(ARTIST, item.getString(MediaMetadataCompat.METADATA_KEY_ARTIST))
            values.put(ALBUM_ART_URL, item.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI))
            values.put(TITLE, item.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
            values.put(WAVEFORM_URL, if (item.containsKey(MediaMetadataCompatExt.METADATA_KEY_WAVEFORM_URL)) item.getString(MediaMetadataCompatExt.METADATA_KEY_WAVEFORM_URL) else "")
            values.put(DURATION, item.getLong(MediaMetadataCompat.METADATA_KEY_DURATION))
            writableDatabase.insertOrThrow(DATABASE_MEDIA_ITEMS_NAME, null, values)
        } catch (e: Exception) {
            // ignore
        }
    }

    fun getMediaItem(mediaId: String): MediaMetadataCompat? {
        val cursor = readableDatabase.query(DATABASE_MEDIA_ITEMS_NAME, null, "$ID=?", arrayOf(mediaId), null, null, null, null)
        if (!cursor.moveToFirst()) {
            return null
        }
        return MediaMetadataCompat.Builder()
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, cursor.getString(cursor.getColumnIndex(ID)))
            .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, cursor.getString(cursor.getColumnIndex(SOURCE)))
            .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, cursor.getString(cursor.getColumnIndex(ARTIST)))
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, cursor.getLong(cursor.getColumnIndex(DURATION)))
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, cursor.getString(cursor.getColumnIndex(ALBUM_ART_URL)))
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, cursor.getString(cursor.getColumnIndex(TITLE)))
            .putString(MediaMetadataCompatExt.METADATA_KEY_WAVEFORM_URL, cursor.getString(cursor.getColumnIndex(WAVEFORM_URL)))
            .putString(MediaMetadataCompatExt.METADATA_KEY_TYPE, MediaMetadataCompatExt.MediaType.MEDIA.name)
            .build()
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_MEDIA_ITEMS_CREATE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        // initial schema is up-to-date
    }
}