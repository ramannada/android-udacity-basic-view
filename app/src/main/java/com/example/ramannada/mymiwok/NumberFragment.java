package com.example.ramannada.mymiwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumberFragment extends Fragment {
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
                    mediaPlayer.pause();
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



    public NumberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.list_view, container, false);

        ArrayList<Words> words = new ArrayList<>();

        words.add(new Words("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Words("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Words("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Words("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Words("five", "massoka", R.drawable.number_five, R.raw.number_five));
        words.add(new Words("six", "temmoka", R.drawable.number_six, R.raw.number_six));
        words.add(new Words("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("eight","kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("ten", "na' aacha", R.drawable.number_ten, R.raw.number_ten));

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        WordsAdapter wordsAdapter = new WordsAdapter (getActivity(), words, R.color.category_numbers);

        ListView listView = rootView.findViewById(R.id.list_view);

        listView.setAdapter(wordsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Words words = (Words) adapterView.getItemAtPosition(i);

                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);


                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    releaseMediaPlayer();
                    mediaPlayer = MediaPlayer.create(getActivity(), words.getAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mpCompletionListener);
                }

            }
        });

        return rootView;
    }


    @Override
    public void onStop() {
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
