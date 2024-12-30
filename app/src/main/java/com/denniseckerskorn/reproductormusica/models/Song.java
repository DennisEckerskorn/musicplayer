package com.denniseckerskorn.reproductormusica.models;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Objects;

public class Song {
    private final String title;
    private final String artist;
    private final String duration;
    private final String album;
    private final Bitmap coverImage;
    private final int resourceId;

    public Song(String title, String artist, String duration, String album, Bitmap coverImage, int resourceId) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
        this.resourceId = resourceId;
        this.coverImage = coverImage;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getDuration() {
        return duration;
    }

    public String getAlbum() {
        return album;
    }

    public int getResourceId() {
        return resourceId;
    }

    public Bitmap getCoverImage() {
        return coverImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return Objects.equals(title, song.title) && Objects.equals(artist, song.artist) && Objects.equals(duration, song.duration) && Objects.equals(album, song.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, artist, duration, album);
    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}
