package com.example.appmarvel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        VideoView video = findViewById(R.id.splashVideoView);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.animacaoreal;
        Uri uri = Uri.parse(videoPath);
        video.setVideoURI(uri);

        // Quando o vídeo terminar, navega para a MainActivity
        video.setOnCompletionListener(mp -> {
            Intent intent = new Intent(Splash.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Inicia o vídeo
        video.start();
    }
}