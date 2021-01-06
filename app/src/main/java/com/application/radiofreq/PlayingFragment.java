package com.application.radiofreq;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaDataSource;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class PlayingFragment extends Fragment{

    TextView playingRadio, playingFrequency;
    ImageView playingImage;
    public MainActivity activity;

    private Button buttonPlay;
    private Button buttonStopPlay;
    private MediaPlayer player;
    boolean paused = true;
    //String url = "https://myradioonline.ro/magic-fm";
    String url2 = "http://astreaming.virginradio.ro:8000/VirginRadio_aac";
    private MediaDataSource mediaDataSource;

    public PlayingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.getSerializable("Title");
        outState.getSerializable("Frequency");
        outState.getSerializable("ImageUrl");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here
            Bundle bundle = this.getArguments();
            if(bundle != null) {
                String title = bundle.getString("Title");
                String frequency = bundle.getString("Frequency");
                String imageUrl = bundle.getString("ImageUrl");

                Bitmap b = null;
                try {
                    InputStream in = new URL(imageUrl).openStream();
                    b = BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                playingRadio.setText(title);
                playingFrequency.setText(frequency);
                playingImage.setImageBitmap(b);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_playing, container, false);

        //initialize the toolbar
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.playing_toolbar);
        toolbar.setTitle("Playing");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).onBackPressed();
            }
        });

        playingRadio = (TextView) v.findViewById(R.id.playingTitle);
        playingFrequency = (TextView) v.findViewById(R.id.playingFrequency);
        playingImage = (ImageView) v.findViewById(R.id.playingImage);

        buttonPlay = (Button) v.findViewById(R.id.bt_stop);
        buttonStopPlay = (Button) v.findViewById(R.id.bt_play); //first displayed button

        buttonStopPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (paused) {
                    Toast.makeText(getActivity(), "Radio was paused, now is singing", Toast.LENGTH_SHORT).show();
                    //initializeMediaPlayer();
                    buttonStopPlay.setVisibility(Button.GONE);
                    buttonPlay.setVisibility(Button.VISIBLE);
                    paused = false;

                    buttonPlay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getActivity(), "Radio is now stopped", Toast.LENGTH_SHORT).show();
                            player.stop();
                            paused = true;
                            buttonStopPlay.setVisibility(Button.VISIBLE);
                            buttonPlay.setVisibility(Button.GONE);
                        }
                    });
                }
            }
        });
        return v;
    }


    //Enable options menu in this fragment
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        //setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //inflate menu
        //inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        player.setAudioAttributes( new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build());

        try {
            //change with setDataSource(Context,Uri);
            player.setDataSource(url2);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    //mp.start();
                    player.start();
                }
            });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
