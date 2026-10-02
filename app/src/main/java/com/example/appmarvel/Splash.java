package com.example.appmarvel;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private static final long SPLASH_DURATION_MS = 9000L; // 9 segundos conforme solicitado
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        // Reproduzir o áudio jarvis_intro_1 da pasta res/raw
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.jarvis_intro_1);
            mediaPlayer.start();
        } catch (Exception e) {
            Log.e("Splash", "Erro ao reproduzir áudio", e);
        }

        // Timer de 12 segundos para ir para a MainActivity e encerrar o áudio
        new Handler().postDelayed(this::navigateToMain, SPLASH_DURATION_MS);
    }

    private void navigateToMain() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            } catch (Exception ignored) {}
            mediaPlayer = null;
        }
        startActivity(new Intent(Splash.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
            } catch (Exception ignored) {}
            mediaPlayer = null;
        }
    }
}
