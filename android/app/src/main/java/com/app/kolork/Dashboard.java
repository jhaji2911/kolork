package com.app.kolork;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.app.kolork.adapter.DashboardPagerAdapter;
import com.app.kolork.databinding.ActivityDashboardBinding;



public class Dashboard extends AppCompatActivity  implements TextToSpeech.OnInitListener{


    

    private ActivityDashboardBinding mBinding;
    public static TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        allocation();
        setEvent();
    }

    private void allocation() {
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
    }

    private void setEvent() {
        mBinding.viewPager.setAdapter(new DashboardPagerAdapter(getSupportFragmentManager(),getApplicationContext()));

        Intent ttsIntent=new Intent();
        ttsIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(ttsIntent,11);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 11) {

            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                tts = new TextToSpeech(this, Dashboard.this);
            } else {
                Intent i = new Intent();
                i.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivityForResult(i,11);
            }
        }
    }


    public static void speechNow(String str) {
        tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            Dashboard.speechNow("starting color detection module");

            //tts.speak("Speech Engine INSTALLED", TextToSpeech.QUEUE_FLUSH, null);
            // Toast.makeText(ctx, "engine installed", Toast.LENGTH_SHORT).show();
        } else if (status == TextToSpeech.ERROR) {
            tts.speak("Wait for Speech Engine", TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(this, "error in engine installed", Toast.LENGTH_SHORT).show();
        }
    }


}