package com.tiefensuche.soundcrowd.plugins

import android.net.Uri
import android.os.Bundle
import androidx.media3.common.HeartRating
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaItem.RequestMetadata
import androidx.media3.common.MediaMetadata
import com.tiefensuche.soundcrowd.plugins.MediaMetadataCompatExt.COMMAND_LIKE
import com.tiefensuche.soundcrowd.plugins.MediaMetadataCompatExt.FROM_RECENT_SEARCH_QUERIES
import com.tiefensuche.soundcrowd.plugins.MediaMetadataCompatExt.METADATA_KEY_DATASOURCE

object MediaItemUtils {
    fun createMediaItem(
        id: String,
        uri: Uri,
        title: String,
        duration: Long,
        artist: String? = null,
        album: String? = null,
        artworkUri: Uri? = null,
        waveform: String? = null,
        url: String? = null,
        rating: HeartRating? = null,
        plugin: String? = null,
        isDataSource: Boolean? = null
    ): MediaItem {
        return MediaItem.Builder()
            .setMediaId(id)
            .setUri(uri)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtist(artist)
                    .setAlbumTitle(album)
                    .setArtworkUri(artworkUri)
                    .setDurationMs(duration)
                    .setIsPlayable(true)
                    .setIsBrowsable(false)
                    .setUserRating(rating)
                    .setExtras(
                        Bundle().also {
                            it.putString(
                                MediaMetadataCompatExt.METADATA_KEY_TYPE,
                                MediaMetadataCompatExt.MediaType.MEDIA.name
                            )
                            it.putString(MediaMetadataCompatExt.METADATA_KEY_WAVEFORM_URL, waveform)
                            it.putString(MediaMetadataCompatExt.METADATA_KEY_URL, url)
                            it.putString(MediaMetadataCompatExt.METADATA_KEY_PLUGIN, plugin)
                            if (isDataSource != null)
                                it.putBoolean(METADATA_KEY_DATASOURCE, isDataSource)
                        }
                    ).also {
                        if (rating != null)
                            it.setSupportedCommands(listOf(COMMAND_LIKE))
                    }
                    .build()
            )
            .setRequestMetadata(
                RequestMetadata.Builder()
                    .setMediaUri(uri)
                    .build()
            )
            .build()
    }

    fun createBrowsableItem(
        id: String,
        title: String,
        type: MediaMetadataCompatExt.MediaType,
        artist: String? = null,
        album: String? = null,
        artworkUri: Uri? = null,
        duration: Long? = null,
        description: String? = null
    ): MediaItem {
        return MediaItem.Builder()
            .setMediaId(id)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(title)
                    .setArtist(artist)
                    .setAlbumTitle(album)
                    .setArtworkUri(artworkUri)
                    .setDurationMs(duration)
                    .setDescription(description)
                    .setIsPlayable(false)
                    .setIsBrowsable(true)
                    .setExtras(
                        Bundle().also {
                            it.putString(MediaMetadataCompatExt.METADATA_KEY_TYPE, type.name)
                        }
                    )
                    .build()
            ).build()
    }

    fun createTextItem(text: String, fromRecent: Boolean): MediaItem {
        return MediaItem.Builder()
            .setMediaId(text)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(text)
                    .setIsPlayable(false)
                    .setIsBrowsable(false)
                    .setExtras(Bundle().apply {
                        putBoolean(
                            FROM_RECENT_SEARCH_QUERIES,
                            fromRecent
                        )
                    })
                    .build()
            ).build()
    }
}