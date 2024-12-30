package com.denniseckerskorn.reproductormusica.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
import com.denniseckerskorn.reproductormusica.service.MusicService;
import com.denniseckerskorn.reproductormusica.utils.SongUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements OnSongClickListener {
    private List<Song> songs;
    private SeekBar seekBar;
    private TextView tvPlayedMin, tvTotalMin, tvSongTitle;
    private ScheduledExecutorService executor;
    private MusicService musicService;
    private boolean isBound = false;
    private ImageButton btnPlay;
    private ImageView ivSongCover;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) iBinder;
            musicService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound = false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupViews();

        try {
            loadSongs();
            setupRecyclerView();
        } catch (Exception e) {
            Log.e("MainActivity", "Error initializing the app", e);
        }

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

        setupSeekBarUpdater();
    }

    @Override
    public void onSongClick(Song song) {
        Log.d("MainActivity", "Song clicked: " + song.getTitle());
        playSong(song);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Music Player");
        setSupportActionBar(toolbar);
    }

    private void setupViews() {
        ivSongCover = findViewById(R.id.ivSongCover);
        tvSongTitle = findViewById(R.id.tvSongTitle);
        tvPlayedMin = findViewById(R.id.tvPlayedMin);
        seekBar = findViewById(R.id.seekBar2);
        tvTotalMin = findViewById(R.id.tvTotalMin);
        ImageButton btnSpeedUp = findViewById(R.id.btnSpeedUp);
        ImageButton btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        ImageButton btnNext = findViewById(R.id.btnNext);
        ImageButton btnShuffle = findViewById(R.id.btnShuffle);

        //*** LISTENERS BOTONES Y SEEKBAR ***

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b && isBound && musicService != null) {
                    musicService.seekTo(i);
                    tvPlayedMin.setText(formatDuration(i));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Opcional: Pausa las actualizaciones del SeekBar mientras el usuario arrastra
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Opcional: Reanuda las actualizaciones del SeekBar después de soltar

            }
        });

        btnSpeedUp.setOnClickListener(v -> {

        });

        btnPrevious.setOnClickListener(v -> {

        });

        //PLAY BUTTON LISTENER
        btnPlay.setOnClickListener(v -> {
            if (isBound && musicService != null) {
                if (musicService.isPlaying()) {
                    musicService.pause();
                } else {
                    if (musicService.getCurrentPosition() > 0) {
                        musicService.resume();
                    } else {
                        if (!songs.isEmpty()) {
                            playSong(songs.get(0));
                        }
                    }
                }
                updatePlayButtonIcon();
            }
        });

        btnNext.setOnClickListener(v -> {

        });

        btnShuffle.setOnClickListener(v -> {

        });


    }

    private void updatePlayButtonIcon() {
        if (musicService.isPlaying()) {
            btnPlay.setImageResource(R.drawable.button_pause);
        } else {
            btnPlay.setImageResource(R.drawable.button_play);
        }
    }

    private void setupRecyclerView() {
        RecyclerView rvSongList = findViewById(R.id.rvSongList);
        SongAdapter songAdapter = new SongAdapter(songs);
        rvSongList.setAdapter(songAdapter);
        songAdapter.setOnSongClickListener(this);
        rvSongList.setHasFixedSize(true);
        rvSongList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }


    private void playSong(Song song) {
        if (isBound && musicService != null) {
            musicService.play(song.getResourceId());
            ivSongCover.setImageBitmap(song.getCoverImage());
            tvSongTitle.setText(song.getTitle());
            seekBar.setMax(musicService.getDuration());
            tvTotalMin.setText(formatDuration(musicService.getDuration()));
        }
    }

    private void loadSongs() {
        songs = new ArrayList<>();
        try {
            songs.add(SongUtils.retrieveSongFromRaw(this, R.raw.dennis_lloyd_gfy));
            songs.add(SongUtils.retrieveSongFromRaw(this, R.raw.dennis_lloyd_nevermind));
            Log.d("MainActivity", "Songs loaded: " + songs.size());
        } catch (IOException e) {
            Log.e("MainActivity", "Error loading songs", e);
        }
    }

    private void setupSeekBarUpdater() {
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(() -> {
            if (isBound && musicService != null) {
                int currentPosition = musicService.getCurrentPosition();
                runOnUiThread(() -> {
                    seekBar.setProgress(currentPosition);
                    tvPlayedMin.setText(formatDuration(currentPosition));
                });
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
    }

    private String formatDuration(int milliseconds) {
        int minutes = milliseconds / 1000 / 60;
        int seconds = (milliseconds / 1000) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
        if (executor != null && !executor.isShutdown()) {
            executor.shutdown();
        }
    }
}