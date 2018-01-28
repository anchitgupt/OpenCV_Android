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
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.raw.com.audiorecorder.R.mipmap.icon;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MediaRecorder mediaRecorder;
    String outputfile;
    ImageButton b1,b2,b3,b4;
    MediaPlayer mediaPlayer;
    ImageButton imageView;
    FloatingActionButton floatingActionButton;
//    RotateAnimation rotateAnimation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);

        b1 = (ImageButton) this.findViewById(R.id.record);
        b2 = (ImageButton) this.findViewById(R.id.stop);
        b3 = (ImageButton) findViewById(R.id.play);
        b4 = (ImageButton) findViewById(R.id.gallery);
        floatingActionButton =(FloatingActionButton)findViewById(R.id.floatingActionButton);
        imageView = (ImageButton) findViewById(R.id.front_image);
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

        if(v == b1){

             b1.setEnabled(false);
             b2.setEnabled(true);
             b3.setEnabled(false);
             b4.setEnabled(false);

            imageView.setImageResource(R.mipmap.recorder);
           // outputfile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/m.amr";

            File sdcard = Environment.getExternalStorageDirectory();
            File folder = new File(sdcard.getAbsolutePath() + "/AudioS");
            if(!folder.exists())
                folder.mkdir();
            String time = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.getDefault()).format(new Date());
            File f2 = new File(folder.getAbsolutePath() + "/AUD_" + time + ".amr");
            Uri uri = Uri.parse(String.valueOf(f2));
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(String.valueOf(f2));

            try {

                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this,"Recording Started...",Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(v== b2){

            b1.setEnabled(true);
            b2.setEnabled(false);
            b3.setEnabled(true);
            b4.setEnabled(true);
            imageView.setImageResource(icon);
            mediaRecorder.stop();
            mediaRecorder.release();
            Toast.makeText(this, "Recording Stopped..", Toast.LENGTH_SHORT).show();

        }

        if(v == b3 || (v == imageView)){

            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);
            b4.setEnabled(true);
            imageView.setImageResource(R.mipmap.au);

            @SuppressLint("SdCardPath") Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()+"/m.amr");
            try {
                //to be asked
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
        if(v == b4){

            b1.setEnabled(true);
            b2.setEnabled(false);
            b3.setEnabled(true);
            b4.setEnabled(false);
            imageView.setImageResource(R.mipmap.recorder);
            Toast.makeText(this, "Reset", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,ListClass.class);
            this.startActivity(intent);
        }
        if((v == findViewById(R.id.floatingActionButton))){

            ApplicationInfo app = getApplicationContext().getApplicationInfo();
            String filePath = app.sourceDir;
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath)));
            startActivity(Intent.createChooser(intent, "Share app via"));
        }
    }
}
