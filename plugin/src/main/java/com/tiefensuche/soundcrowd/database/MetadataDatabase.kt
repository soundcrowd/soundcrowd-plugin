/*
 * SPDX-License-Identifier: GPL-3.0-only
 */

package com.tiefensuche.soundcrowd.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.support.v4.media.MediaMetadataCompat
import android.util.Log
import com.tiefensuche.soundcrowd.extensions.MediaMetadataCompatExt
import org.json.JSONObject

/**
 * Database that stores media items, metadata, and [CuePoint] items
 *
 * Created by tiefensuche on 23.09.16.
 */
open class MetadataDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private var TAG = this::class.java.simpleName
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

        var instance: MetadataDatabase? = null

        fun getInstance(context: Context): MetadataDatabase {
            var instance = instance
            if (instance == null) {
                instance = MetadataDatabase(context)
                this.instance = instance
            }
            return instance
        }
    }

    init {
        writableDatabase.execSQL(DATABASE_MEDIA_ITEMS_CREATE)
    }

    fun addMediaItem(item: MediaMetadataCompat) {
        addMediaItem(MediaMetadataCompatExt.toJSON(item))
    }

    fun addMediaItem(item: JSONObject) {
        val values = ContentValues()
        val mediaId = item.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
        values.put(ID, mediaId.substring(mediaId.indexOf('|') + 1))
        values.put(SOURCE, item.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI))
        values.put(ARTIST, item.getString(MediaMetadataCompat.METADATA_KEY_ARTIST))
        values.put(ALBUM_ART_URL, item.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI))
        values.put(TITLE, item.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
        values.put(WAVEFORM_URL, if (item.has(MediaMetadataCompatExt.METADATA_KEY_WAVEFORM_URL)) item.getString(MediaMetadataCompatExt.METADATA_KEY_WAVEFORM_URL) else "")
        values.put(DURATION, item.getLong(MediaMetadataCompat.METADATA_KEY_DURATION))
        try {
            writableDatabase.insertOrThrow(DATABASE_MEDIA_ITEMS_NAME, null, values)
        } catch (e: SQLException) {
            Log.d(TAG, "value=$values", e)
        }
    }

    fun getMediaItem(mediaId: String): JSONObject? {
        val cursor = readableDatabase.query(DATABASE_MEDIA_ITEMS_NAME, null, "$ID=?", arrayOf(mediaId), null, null, null, null)
        if (cursor.moveToFirst()) {
            return buildItem(cursor)
        }
        return null
    }

    private fun buildItem(cursor: Cursor): JSONObject {
        val json = JSONObject()
        json.put(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, cursor.getString(cursor.getColumnIndex(ID)))
                .put(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, cursor.getString(cursor.getColumnIndex(SOURCE)))
                .put(MediaMetadataCompat.METADATA_KEY_ARTIST, cursor.getString(cursor.getColumnIndex(ARTIST)))
                .put(MediaMetadataCompat.METADATA_KEY_DURATION, cursor.getLong(cursor.getColumnIndex(DURATION)))
                .put(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, cursor.getString(cursor.getColumnIndex(ALBUM_ART_URL)))
                .put(MediaMetadataCompat.METADATA_KEY_TITLE, cursor.getString(cursor.getColumnIndex(TITLE)))
                .put(MediaMetadataCompatExt.METADATA_KEY_WAVEFORM_URL, cursor.getString(cursor.getColumnIndex(WAVEFORM_URL)))
                .put(MediaMetadataCompatExt.METADATA_KEY_SOURCE, "Cache")
                .put(MediaMetadataCompatExt.METADATA_KEY_TYPE, MediaMetadataCompatExt.MediaType.MEDIA.name)
        return json
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_MEDIA_ITEMS_CREATE)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        // initial schema is up-to-date
    }
}