package com.example.ramannada.mymiwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhraseActiviy extends AppCompatActivity{
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
        setContentView(R.layout.activity_phrase_activiy);

        ArrayList<Words> phrase = new ArrayList<>();

        phrase.add(new Words("Where are you going?", "Kemana kamu pergi?", R.raw.phrase_where_are_you_going));
        phrase.add(new Words("What is your name?", "Siapa namamu?", R.raw.phrase_what_is_your_name));
        phrase.add(new Words("How are you feeling?", "Apa yang kamu rasakan?", R.raw.phrase_how_are_you_feeling));
        phrase.add(new Words("Let's go!", "Ayo!", R.raw.phrase_lets_go));
        phrase.add(new Words("Come here", "Kesini", R.raw.phrase_come_here));

        WordsAdapter wordsAdapter = new WordsAdapter(this, phrase, R.color.category_phrase);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        ListView listView = (ListView) findViewById(R.id.list_view_phrase);
        listView.setAdapter(wordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Words words = (Words) adapterView.getItemAtPosition(i);

                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    releaseMediaPlayer();
                    mediaPlayer = MediaPlayer.create(PhraseActiviy.this, words.getAudio());
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
