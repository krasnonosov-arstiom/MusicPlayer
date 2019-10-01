package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    ImageButton playPauseButton;
    SeekBar seekBar;
    TextView fullPlayingTime;
    TextView currentPlayingTime;
    ImageButton nextImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.usual);
        playPauseButton = findViewById(R.id.playPauseButton);

        seekBar = findViewById(R.id.seekBar);
        int playingTime = mediaPlayer.getDuration();
        seekBar.setMax(playingTime);

        fullPlayingTime = findViewById(R.id.fullPlayingTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        fullPlayingTime.setText(simpleDateFormat.format(new Date(mediaPlayer.getDuration())));

        currentPlayingTime = findViewById(R.id.currentPlayingTime);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                int currenPosition = mediaPlayer.getCurrentPosition();
                currentPlayingTime.setText(simpleDateFormat.format( new Date(currenPosition)));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        nextImageButton = findViewById(R.id.nextButton);
    }

    public void playPause(View view) {

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            playPauseButton.setImageResource(R.drawable.play);
        } else {
            mediaPlayer.start();
            playPauseButton.setImageResource(R.drawable.pause);
        }


    }

    public void previous(View view) {
        mediaPlayer.seekTo(0);
        mediaPlayer.pause();
        playPauseButton.setImageResource(R.drawable.play);
    }

    public void next(View view) {
        mediaPlayer.seekTo(mediaPlayer.getDuration());
        playPauseButton.setImageResource(R.drawable.play);
    }
}
