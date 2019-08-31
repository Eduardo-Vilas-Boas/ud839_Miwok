package com.example.android.miwok;

import android.support.annotation.NonNull;

/**
 * This class stores the miwok word/phrase and its translation,
 * and an image id if there is an image associated
 */
class Word {

    /*translation for the language of the user*/
    private String defaultTranslation;

    /*miwok word/phrase*/
    private String miwokTranslation;

    /*image resource ID for the image associated with the miwok word/phrase*/
    private int imageResourceId = NO_IMAGE_PROVIDED;

    private static int NO_IMAGE_PROVIDED = -1;

    /*audio resource ID for the audio associated with the miwok word/phrase*/
    private int audioResourceId;

    /**
     * This constructor is for the PhrasesActivity, because phrases don't have an image associated
     *
     * @param defaultTranslation - defaultTranslation
     * @param miwokTranslation - miwokTranslation
     */
    Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.audioResourceId = audioResourceId;
    }

    /**
     * This constructor is for all activities, except phrases, that do not require an image
     *
     * @param defaultTranslation - defaultTranslation
     * @param miwokTranslation - miwokTranslation
     * @param imageResourceId - imageResourceId
     */
    Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId){
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
    }

    /**
     *
     * @return default translation for the user mother tongue
     */
    String getDefaultTranslation() {
        return defaultTranslation;
    }

    /**
     *
     * @return miwok translation
     */
    String getMiwokTranslation() {
        return miwokTranslation;
    }

    /**
     *
     * @return image resource ID of the image associated with the miwok
     */
    int getImageResourceId() { return imageResourceId; }

    /**
     *
     * @return audio resource ID of the audio associated with the miwok
     */
    int getAudioResourceId() { return audioResourceId; }

    @NonNull
    @Override
    public String toString() {
        return "Word{" +
                "defaultTranslation='" + defaultTranslation + '\'' +
                ", miwokTranslation='" + miwokTranslation + '\'' +
                ", imageResourceId=" + imageResourceId +
                ", audioResourceId=" + audioResourceId +
                '}';
    }
}
