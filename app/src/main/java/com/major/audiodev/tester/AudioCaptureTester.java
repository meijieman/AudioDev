package com.major.audiodev.tester;

import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.os.Environment;

import com.major.audiodev.audio.AudioCapturer;
import com.major.audiodev.wav.WavFileWriter;

import java.io.IOException;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/8/20 12:53
 */
public class AudioCaptureTester implements Tester,  AudioCapturer.OnAudioFrameCapturedListener  {

    private static final String DEFAULT_TEST_FILE = Environment.getExternalStorageDirectory() + "/test.wav";

    private AudioCapturer mAudioCapturer;
    private WavFileWriter mWavFileWriter;

    @Override
    public boolean startTesting(){
        mAudioCapturer = new AudioCapturer();
        mWavFileWriter = new WavFileWriter();
        try {
            mWavFileWriter.openFile(DEFAULT_TEST_FILE, 44100, 1, 16);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        mAudioCapturer.setOnAudioFrameCapturedListener(this);
        return mAudioCapturer.startCapture(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT);
    }

    @Override
    public boolean stopTesting(){
        mAudioCapturer.stopCapture();
        try {
            mWavFileWriter.closeFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onAudioFrameCaptured(byte[] audioData){
        mWavFileWriter.writeData(audioData, 0, audioData.length);
    }
}
