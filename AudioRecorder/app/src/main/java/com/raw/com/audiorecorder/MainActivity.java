package com.raw.com.audiorecorder;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static com.raw.com.audiorecorder.R.mipmap.icon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   Button b1,b2,b3,b4;
    MediaRecorder mediaRecorder;
    String outputfile;
    MediaPlayer mediaPlayer;
    ImageView imageView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button)this.findViewById(R.id.button);
        b2 =(Button)this.findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        floatingActionButton =(FloatingActionButton)findViewById(R.id.floatingActionButton);
        imageView =(ImageView)findViewById(R.id.imageView);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        mediaPlayer = new MediaPlayer();
        if(v == this.findViewById(R.id.button)){
             b1.setEnabled(false);
             b2.setEnabled(true);
             b3.setEnabled(false);
             b4.setEnabled(false);
            imageView.setImageResource(R.mipmap.recorder);
            outputfile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/m.amr";
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(outputfile);
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this,"Recording Started...",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(v== this.findViewById(R.id.button2)){
            b1.setEnabled(true);
            b2.setEnabled(false);
            b3.setEnabled(true);
            b4.setEnabled(true);
            imageView.setImageResource(icon);
            mediaRecorder.stop();
            mediaRecorder.release();
            Toast.makeText(this, "Recording Stopped..", Toast.LENGTH_SHORT).show();

        }
        if(v == this.findViewById(R.id.button3) || (v == this.findViewById(R.id.imageView))){
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b4.setEnabled(true);
            imageView.setImageResource(R.mipmap.au);
            @SuppressLint("SdCardPath") Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/m.amr");
            try {
                mediaPlayer.setDataSource(String.valueOf(uri));

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
        if(v == findViewById(R.id.button4)){
            b1.setEnabled(true);
            b2.setEnabled(false);
            b3.setEnabled(true);
            b4.setEnabled(false);
            imageView.setImageResource(R.mipmap.recorder);
            Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
        }
        if((v == findViewById(R.id.floatingActionButton)){
            ApplicationInfo app = getApplicationContext().getApplicationInfo();
            String filePath = app.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
            startActivity(Intent.createChooser(intent, "Share app via"));
        }
    }
}
