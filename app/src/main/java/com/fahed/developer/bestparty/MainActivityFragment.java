package com.fahed.developer.bestparty;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewFlipper;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //Atributos de View Flipper
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private Context mContext;
    private Animation.AnimationListener mAnimationListener;
    private  View rootView;
    //Atributos generales
    private VideoView videoView;
    private TextView tvHola;
    private Typeface FuentePersonalizado;


    //Detector de Gestura de la Actividad
    private final GestureDetector detector = new GestureDetector(getActivity(),
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    try {
                        // show next y show previus se utiliza para mostrar la proxmina o anterior item de ViewFlipper
                        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                            mViewFlipper.showNext();
                            return true;
                        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                            mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                            mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_out));
                            mViewFlipper.showPrevious();
                            return true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        rootView= inflater.inflate(R.layout.fragment_main, container, false);

        //Recuperar Witgets
        tvHola=(TextView)rootView.findViewById(R.id.tvHola);

        tvHola.setTypeface(FuentePersonalizado);


        //Inicarlizar el video automaticamente
        try {
            videoView = (VideoView) rootView.findViewById(R.id.vvIntro);
            Uri path = Uri.parse("android.resource://com.fahed.developer.nature/" + R.raw.intros);
            videoView.setVideoURI(path);
            videoView.start();
            //Repetir video automaticamente
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer videoView) {
                    videoView.setLooping(true);
                }
            });
        } catch (Exception e) {
            Log.e("", "Error: " + e.toString());
        }
        //View Flipper
        mContext = rootView.getContext();
        mViewFlipper = (ViewFlipper) rootView.findViewById(R.id.vfInformacion);
        //Inicilaizar automaticamente View Flipper
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(7000);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
        mViewFlipper.startFlipping();
        //Detectar el toch de la actividad
        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });


        return rootView;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        FuentePersonalizado= Typeface.createFromAsset(getActivity().getAssets(), "Sansation-Regular.ttf");
    }
    @Override
    public void onResume() {
        super.onResume();
        videoView.start();
    }
}
