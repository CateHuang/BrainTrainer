package com.mycompany.braintrainer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RelativeLayout playLayout;
    Button startButton, playButton;
    TextView timerTextView, textView5, textView0, textView1, textView2, textView3;
    int randomNumber1, randomNumber2, sum, answer;
    int timerMax = 30;
    int round, score;
    int [] values = {0, 0, 0, 0};
    Boolean isActive = false;
    MediaPlayer mplayer0, mplayer1;

    public void createRandomNumbers(){
        // generate random number between 10-49, 0-3
        randomNumber1 = (int)(Math.random() * 39 + 10);
        randomNumber2 = (int)(Math.random() * 39 + 10);
        sum = randomNumber1 + randomNumber2;
    }

    public void setNumbers() {
        TextView questionTextView = (TextView) findViewById(R.id.questionTextView);
        createRandomNumbers();
        values[0] = sum;
        answer = sum;
        questionTextView.setText(Integer.toString(randomNumber1) + " + " + Integer.toString(randomNumber2));

        for ( int i = 1; i < 4; i++ ) {
            createRandomNumbers();
            // check the sum of numbers is unique
            for ( int j = i - 1; j >= 0; j-- ) {
                while (sum == values[j]) {
                    createRandomNumbers();
                }
            }
            values[i] = sum;
        }

        Arrays.sort(values);

        textView0.setText(" " + values[0] + " ");
        textView1.setText(" " + values[1] + " ");
        textView2.setText(" " + values[2] + " ");
        textView3.setText(" " + values[3] + " ");
    }

    public void updateTimer(int time) {
        timerTextView = (TextView) findViewById(R.id.timerTextView);

        CountDownTimer countDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long l) {
                timerTextView.setText(Integer.toString((int) l / 1000) + " s");
            }

            @Override
            public void onFinish() {
                isActive = false;
                textView5.setText("Your score is " + Integer.toString(score) + "/" + Integer.toString(round));
                playButton.setVisibility(View.VISIBLE);
                playButton.setText("Play again");
            }
        }.start();
    }

    public void updateScore() {
        TextView scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        scoreTextView.setText(Integer.toString(score) + "/" + Integer.toString(round));
    }

    public void playAgain(View view) {
        initial();
    }

    public void initial() {
        isActive = true;
        updateTimer(timerMax);
        setNumbers();
        score = 0;
        round = 0;
        updateScore();
        playButton.setVisibility(View.INVISIBLE);
        textView5.setVisibility(View.INVISIBLE);
    }

    public void playSound(int CORRECT){
        if ( CORRECT == 1)  {
            mplayer1.start();
        } else {
            mplayer0.start();
        }
    }

    public void clickTextView( View view ) {

        if ( isActive == true ) {
            TextView textView = (TextView) view;
            int clickedTag = Integer.parseInt(textView.getTag().toString());

            if (values[clickedTag] == answer) {
                //playSound(1);
                textView5.setVisibility(View.VISIBLE);
                textView5.setText("CORRECT");
                score++;
            } else {
                //playSound(0);
                textView5.setVisibility(View.VISIBLE);
                textView5.setText("WRONG");
            }
            round++;
            updateScore();
            setNumbers();
        }
    }

    public void startGame( View view ) {
        startButton.setVisibility(View.INVISIBLE);
        playLayout.setVisibility(View.VISIBLE);
        initial();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playLayout = (RelativeLayout) findViewById(R.id.playLayout);
        startButton = (Button) findViewById(R.id.startButton);
        playButton = (Button) findViewById(R.id.playButton);

        textView0 = (TextView) findViewById(R.id.textView0);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView3);
        textView5 = (TextView) findViewById(R.id.textView5);

        mplayer0 = MediaPlayer.create(getApplicationContext(), R.raw.smb_jump);
        mplayer1 = MediaPlayer.create(getApplicationContext(), R.raw.smb_coin);
    }
}
