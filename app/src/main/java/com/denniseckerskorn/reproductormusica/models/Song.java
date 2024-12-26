package com.denniseckerskorn.reproductormusica.models;

import java.util.Objects;

public class Song {
    private final String title;
    private final String artist;
    private final long duration;
    private final String album;

    public Song(String title, String artist, long duration, String album) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.album = album;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public long getDuration() {
        return duration;
    }

    public String getAlbum() {
        return album;
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
