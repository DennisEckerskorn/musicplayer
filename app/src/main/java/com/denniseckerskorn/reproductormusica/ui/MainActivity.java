package com.denniseckerskorn.reproductormusica.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denniseckerskorn.reproductormusica.R;
import com.denniseckerskorn.reproductormusica.adapter.SongAdapter;
import com.denniseckerskorn.reproductormusica.listeners.OnSongClickListener;
import com.denniseckerskorn.reproductormusica.models.Song;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSongClickListener {
    private List<Song> songs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupViews();

        songs = null;
        setupRecyclerView();



    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Music Player");
        setSupportActionBar(toolbar);
    }

    private void setupViews() {
        ImageView ivSongCover = findViewById(R.id.ivSongCover);
        TextView tvSongTitle = findViewById(R.id.tvSongTitle);
        TextView tvPlayedMin = findViewById(R.id.tvPlayedMin);
        SeekBar seekBar = findViewById(R.id.seekBar2);
        TextView tvTotalMin = findViewById(R.id.tvTotalMin);
        ImageButton btnSpeedUp = findViewById(R.id.btnSpeedUp);
        ImageButton btnPrevious = findViewById(R.id.btnPrevious);
        ImageButton btnPlay = findViewById(R.id.btnPlay);
        ImageButton btnNext = findViewById(R.id.btnNext);
        ImageButton btnShuffle = findViewById(R.id.btnShuffle);
    }

    private void setupRecyclerView() {
        RecyclerView rvSongList = findViewById(R.id.rvSongList);
        SongAdapter songAdapter = new SongAdapter(songs);
        rvSongList.setAdapter(songAdapter);
        songAdapter.setOnSongClickListener(this);
        rvSongList.setHasFixedSize(true);
        rvSongList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSongClick(Song song) {
        Log.d("MainActivity", "Song clicked: " + song.getTitle());
    }
}