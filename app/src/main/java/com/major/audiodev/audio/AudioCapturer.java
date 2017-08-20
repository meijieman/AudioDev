package com.major.audiodev.audio;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import com.major.audiodev.SL;

/**
 * @desc: TODO
 * @author: Major
 * @since: 2017/8/20 12:55
 */
public class AudioCapturer{

    private static final int DEFAULT_SOURCE = MediaRecorder.AudioSource.MIC;
    private static final int DEFAULT_SAMPLE_RATE = 44100;
    private static final int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static final int DEFAULT_DATA_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

    // Make sure the sample size is the same in different devices
    private static final int SAMPLES_PER_FRAME = 1024;

    private boolean mIsStarted;
    private boolean mIsLoopExit;

    private AudioRecord mAudioRecord;
    private Thread mCaptureThread;

    public boolean isStarted(){
        return mIsStarted;
    }

    public boolean startCapture(){
        return startCapture(DEFAULT_SOURCE, DEFAULT_SAMPLE_RATE, DEFAULT_CHANNEL_CONFIG, DEFAULT_DATA_FORMAT);
    }

    public boolean startCapture(int audioSource, int sampleRateInHz, int channelConfig, int audioFormat){
        if(mIsStarted){
            SL.i("Capture already started");
            return false;
        }

        int minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
        if(minBufferSize == AudioRecord.ERROR_BAD_VALUE){
            SL.e("Invalid param!");
            return false;
        }
        mAudioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, minBufferSize * 4);
        if(mAudioRecord.getSampleRate() == AudioRecord.STATE_UNINITIALIZED){
            SL.e("init fail");
            return false;
        }
        mAudioRecord.startRecording();
        ;

        mIsLoopExit = false;
        mCaptureThread = new Thread(new AudioCaptureRunnable());
        mCaptureThread.start();
        mIsStarted = true;
        SL.i("start success!");
        return true;
    }

    public void stopCapture(){
        if(!mIsStarted){
            return;
        }
        mIsLoopExit = true;
        try{
            mCaptureThread.interrupt();
            mCaptureThread.join(1000);
        } catch(InterruptedException e){
            SL.e("stop err");
            e.printStackTrace();
        }

        if(mAudioRecord.getRecordingState() == AudioRecord.RECORDSTATE_RECORDING){
            mAudioRecord.stop();
        }
        mAudioRecord.release();

        mIsStarted = false;
        mListener = null;

        SL.i("stop success");
    }

    private class AudioCaptureRunnable implements Runnable{

        @Override
        public void run(){
            while(!mIsLoopExit){
                byte[] buffer = new byte[SAMPLES_PER_FRAME * 2];
                int ret = mAudioRecord.read(buffer, 0, buffer.length);
                if(ret == AudioRecord.ERROR_INVALID_OPERATION){
                    SL.e("读数据失败 ERROR_INVALID_OPERATION");
                } else if(ret == AudioRecord.ERROR_BAD_VALUE){
                    SL.e("读数据失败 ERROR_BAD_VALUE");
                } else {
                    SL.e("读数据成功 " + buffer.length);
                    if(mListener != null){
                        mListener.onAudioFrameCaptured(buffer);
                    }
                }
            }
        }
    }

    private OnAudioFrameCapturedListener mListener;

    public void setOnAudioFrameCapturedListener(OnAudioFrameCapturedListener listener){
        mListener = listener;
    }

    public interface OnAudioFrameCapturedListener{

        void onAudioFrameCaptured(byte[] audioData);
    }
}
