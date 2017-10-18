package com.example.ramannada.mymiwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private MediaPlayer.OnCompletionListener mpCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            if (mediaPlayer != null) {
                releaseMediaPlayer();
            }
        }
    };

    private AudioManager.OnAudioFocusChangeListener afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int i) {
            switch (i) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    mediaPlayer.pause();
                    break;
                case  AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK:
                    mediaPlayer.pause();;
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseMediaPlayer();
                    break;
                default:
                    mediaPlayer.pause();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color);

        ArrayList<Words> words = new ArrayList<>();

        words.add(new Words("red", "wetetti", R.drawable.color_red, R.raw.color_red));
        words.add(new Words("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Words("brown", "takaakki,", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Words("gray", "toppoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Words("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Words("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Words("dusty yellow", "topissa", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Words("mustard yellow", "chiwiita", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
//        words.add(new Words("blue", "biru", R.drawable.));

        WordsAdapter wordsAdapter = new WordsAdapter(this, words, R.color.category_color);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ListView listView = (ListView) findViewById(R.id.list_view_color);

        listView.setAdapter(wordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Words words = (Words) adapterView.getItemAtPosition(i);

                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    releaseMediaPlayer();
                    mediaPlayer = MediaPlayer.create(ColorActivity.this, words.getAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mpCompletionListener);
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        if (this.mediaPlayer != null) {
            this.mediaPlayer.release();

            this.mediaPlayer = null;

            audioManager.abandonAudioFocus(afChangeListener);
        }
    }
}
