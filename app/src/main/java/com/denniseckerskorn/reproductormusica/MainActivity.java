package com.denniseckerskorn.reproductormusica;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Music Player");
        setSupportActionBar(toolbar);

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
        RecyclerView rvSongList = findViewById(R.id.rvSongList);
    }
}