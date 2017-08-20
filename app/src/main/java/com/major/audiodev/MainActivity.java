package com.major.audiodev;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.major.audiodev.tester.AudioCaptureTester;
import com.major.audiodev.tester.Tester;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner mSpinner;
    private Tester mTester;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSpinner = (Spinner)findViewById(R.id.sp_main);
        findViewById(R.id.btn_start).setOnClickListener(this);
        findViewById(R.id.btn_stop).setOnClickListener(this);

        String[] arr = new String[]{"录制 wav 文件", "播放 wav 文件",
                "OpenSL ES 录制", "OpenSL ES 播放", "音频编解码"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);

        mSpinner.setAdapter(adapter);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.btn_start:
                startTest();
                break;
            case R.id.btn_stop:
                if(mTester != null){
                    mTester.stopTesting();
                    Toast.makeText(this, "stop testing!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void startTest(){
        switch(mSpinner.getSelectedItemPosition()) {
            case 0:
                mTester = new AudioCaptureTester();
                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
        }

        if(mTester != null){
            mTester.startTesting();
            Toast.makeText(this, "start testing!", Toast.LENGTH_SHORT).show();
        }
    }

}
