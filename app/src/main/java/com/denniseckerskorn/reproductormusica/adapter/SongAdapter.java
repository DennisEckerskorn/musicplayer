package com.denniseckerskorn.reproductormusica.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.denniseckerskorn.reproductormusica.R;
import com.denniseckerskorn.reproductormusica.listeners.OnSongClickListener;
import com.denniseckerskorn.reproductormusica.models.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songs;
    private OnSongClickListener onSongClickListener;

    public SongAdapter(List<Song> songs) {
        this.songs = songs;
    }

    public void setOnSongClickListener(OnSongClickListener listener) {
        this.onSongClickListener = listener;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.bindSong(song);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public class SongViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvSongTitleItem;
        private TextView tvSongArtistItem;
        private TextView tvSongDurationItem;
        private TextView tvSongAlbumItem;
        private ImageView ivSongImageItem;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSongTitleItem = itemView.findViewById(R.id.tvSongTitleItem);
            tvSongArtistItem = itemView.findViewById(R.id.tvSongArtistItem);
            tvSongDurationItem = itemView.findViewById(R.id.tvSongDurationItem);
            tvSongAlbumItem = itemView.findViewById(R.id.tvSongAlbumItem);
            ivSongImageItem = itemView.findViewById(R.id.ivSongImageItem);
            itemView.setOnClickListener(this);
        }

        public void bindSong(Song song) {
            tvSongTitleItem.setText(song.getTitle());
            tvSongArtistItem.setText(song.getArtist());
            tvSongDurationItem.setText(formatDuration(song.getDuration()));
            tvSongAlbumItem.setText(song.getAlbum());
            //ivSongImageItem.setImageResource(song.getImage());
        }

        private String formatDuration(long durationMillis) {
            long seconds = durationMillis / 1000;
            long minutes = seconds / 60;
            long remainingSeconds = seconds % 60;
            return String.format("%02d:%02d", minutes, remainingSeconds);
        }

        @Override
        public void onClick(View view) {
            if (onSongClickListener != null) {
                onSongClickListener.onSongClick(songs.get(getAdapterPosition()));
            }
        }
    }
}
