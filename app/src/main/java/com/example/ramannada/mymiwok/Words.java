package com.example.ramannada.mymiwok;

/**
 * Created by ramannada on 10/16/2017.
 */

public class Words {
    private String original, miwok;
    private int imageID = INT_NULL;
    private int audio = INT_NULL;
    private static final int INT_NULL = -1;

    public Words(String original, String miwok) {
        this.original = original;
        this.miwok = miwok;
    }

    public Words(int imageID, String miwok, String original) {
        this.original = original;
        this.miwok = miwok;
        this.imageID = imageID;
    }

    public Words(String original, String miwok, int imageID, int audio) {
        this.original = original;
        this.miwok = miwok;
        this.imageID = imageID;
        this.audio = audio;
    }

    public Words(String original, String miwok, int audio) {
        this.original = original;
        this.miwok = miwok;
        this.audio = audio;
    }

    public String getOriginal() {
        return original;
    }

    public String getMiwok() {
        return miwok;
    }

    public int getImageID() {
        return imageID;
    }

    public int getAudio() {
        return audio;
    }

    public boolean hasImage() {
        return imageID != INT_NULL;
    }

    public boolean hasAudio() {
        return audio != INT_NULL;
    }
}
