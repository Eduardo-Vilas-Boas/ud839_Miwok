package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {
    MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        private void pause(){
            mMediaPlayer.pause();
            mMediaPlayer.seekTo(0);
        }

        @Override
        public void onAudioFocusChange(int i) {
            switch(i){
                case AudioManager.AUDIOFOCUS_GAIN:
                    //Resume playback
                    mMediaPlayer.start();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    //Stop playback
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    pause();
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    pause();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("father","әpә",
                R.drawable.family_father, R.raw.family_father));
        words.add(new Word("mother","әṭa",
                R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("son","angsi",
                R.drawable.family_son, R.raw.family_son));
        words.add(new Word("daughter","tune",
                R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother","taachi",
                R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother","chalitti",
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister","teṭe",
                R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister","kolliti",
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother","ama",
                R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather","paapa",
                R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter itemsAdapter = new WordAdapter(this, words, R.color.category_family);

        ListView listView = findViewById(R.id.list);
        assert listView != null;
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                // Get the {@link Word} object at the given position the user clicked on
                Word word = words.get( i);

                //request audio Focus for playback
                int result = mAudioManager.requestAudioFocus( mOnAudioFocusChangeListener,
                        //use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    //We have audio focus

                    // Create and setup the {@link MediaPlayer} for the audio resource associated
                    // with the current word
                    mMediaPlayer = MediaPlayer.create(FamilyActivity.this, word.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            // Now that the sound file has finished playing, release the media player resources.
                            releaseMediaPlayer();
                        }
                    });

                    Log.v("FamilyActivity", "Current word: " + word);
                }
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks
            mAudioManager.abandonAudioFocus( mOnAudioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
