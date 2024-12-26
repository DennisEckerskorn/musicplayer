package com.denniseckerskorn.reproductormusica.utils;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaMetadataRetriever;

import com.denniseckerskorn.reproductormusica.models.Song;

public class SongUtils {

    public static Song retrieveSongFromRaw(Context context, int rawResId) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        Resources resources = context.getResources();
        mmr.setDataSource(resources.openRawResourceFd(rawResId).getFileDescriptor());

        //Se extraen los metadatos:
        String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);

        return null;
    }
}
