package com.denniseckerskorn.reproductormusica.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaMetadataRetriever;

import com.denniseckerskorn.reproductormusica.models.Song;

import java.io.IOException;

public class SongUtils {

    public static Song retrieveSongFromRaw(Context context, int rawResId) throws IOException {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        Resources resources = context.getResources();

        try {
            // Configura el archivo de audio
            try (android.content.res.AssetFileDescriptor afd = resources.openRawResourceFd(rawResId)) {
                if (afd == null) {
                    throw new IOException("No se pudo abrir el recurso raw.");
                }
                mmr.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            }

            // Extrae los metadatos
            String title = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            String durationMs = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            byte[] coverImage = mmr.getEmbeddedPicture();

            Bitmap coverBitmap = null;
            if(coverImage != null) {
                coverBitmap = BitmapFactory.decodeByteArray(coverImage, 0, coverImage.length);
            }

            // Formatea la duración
            String formattedDuration = formatDuration(durationMs);

            // Crea y retorna el objeto Song
            return new Song(
                    title != null ? title : "Unknown Title",
                    artist != null ? artist : "Unknown Artist",
                    album != null ? album : "Unknown Album", formattedDuration,
                    coverBitmap,
                    rawResId
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error al procesar los metadatos del recurso raw.", e);
        } finally {
            // Libera los recursos del MediaMetadataRetriever
            mmr.release();
        }
    }

    /**
     * Formatea la duración de milisegundos a "MM:SS".
     */
    private static String formatDuration(String durationMs) {
        if (durationMs == null) return "00:00";

        try {
            int duration = Integer.parseInt(durationMs);
            int minutes = duration / 1000 / 60;
            int seconds = (duration / 1000) % 60;
            return String.format("%02d:%02d", minutes, seconds);
        } catch (NumberFormatException e) {
            return "00:00";
        }
    }
}

