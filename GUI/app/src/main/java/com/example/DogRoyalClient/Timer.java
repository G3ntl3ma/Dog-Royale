/**
 * @author: leisen
 */
package com.example.DogRoyalClient;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.example.DogRoyalClient.databinding.FragmentGameBoardBinding;

import java.util.Locale;

public class Timer  extends AppCompatActivity{
    private long startTimeMillis;
    private boolean timerRunning;
    private CountDownTimer countdowntimer;
    private long timeLeftMillis;
    private boolean hasFinished;
    private TimerviewModel viewModel;

    public Timer(long startTimeMillis) {
        this.startTimeMillis = startTimeMillis;
        this.viewModel = MainActivity.getTimerViewModel();
        this.timeLeftMillis = startTimeMillis;
    }

    /** Der Timer zählt runter
     *
     */
    public void startTimer(){
        countdowntimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftMillis = l;
                //System.out.println("Timer ist Ticking!");
                updateCountdown();
            }

            @Override
            public void onFinish() {
                hasFinished = true;
            }
        }.start();
        //System.out.println("Timer has Started!");
        timerRunning = true;
        countdowntimer.start();
    }

    /**Der Timer wird angehalten
     *
     */
    public void pauseTimer(){
        countdowntimer.cancel();
        timerRunning = false;
    }

    /**Der Timer wird auf die startTime zurückgesetzt
     *
     */
    public void resetTimer(){
        pauseTimer();
        timeLeftMillis = startTimeMillis;
        updateCountdown();
    }


    /** Gibt zurück ob der Timer läuft oder nicht
     *
     * @return boolean
     */
    public boolean isTimerRunning(){
        return timerRunning;
    }

    /** gibt die Verbleibende Zeit des Timers in Millisekunden zurück
     *
     * @return long
     */
    public long getTimeLeftMillis(){
        return timeLeftMillis;
    }
    /**
     *Der Timer zählt von dem übergebenem Parameter herab
     *
     * @param millis Die neue Zeit in Millisekunden
     **/
    public void setStartTimeMillis(long millis){
        pauseTimer();
        startTimeMillis = millis;
        timeLeftMillis = millis;
    }
    private void updateCountdown(){
        //System.out.println("Counter is getting updated!");
        int minutes = (int) Math.floor(timeLeftMillis / 1000 / 60);
        int seconds = (int) Math.floor(timeLeftMillis / 1000 % 60);
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes, seconds);
        //System.out.println(timeLeftFormatted);
        viewModel.setTime(timeLeftFormatted);
    }
}

