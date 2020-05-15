package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    final String TAG = "Whack-a-Mole 2.0";
    TextView score;
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8. *DONE*
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly. *DONE*
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    CountDownTimer readyTimer;
    CountDownTimer MoleTimer;
    private void readyTimer(){
        readyTimer = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
                Toast.makeText(Main2Activity.this, "Get Ready In "+millisUntilFinished/1000+" Seconds", Toast.LENGTH_SHORT).show();
            }

            public void onFinish(){
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(Main2Activity.this, "GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                readyTimer.cancel();
            }
        };
        readyTimer.start();
    }

    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        MoleTimer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long l) {
                setNewMole();
                Log.v(TAG, "New Mole Location!");
            }

            @Override
            public void onFinish() {
                MoleTimer.start();
            }
        };
        MoleTimer.start();
    }
    private static final int[] BUTTON_IDS = {
            /* HINT:
                Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
                You may use if you wish to change or remove to suit your codes.*/
            R.id.button,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent thisIntent = getIntent();
        final int startingScore = Integer.parseInt(thisIntent.getStringExtra("Score"));
        Log.v(TAG, "Current User Score: " + startingScore);
        score = findViewById(R.id.advancedscore);
        score.setText(String.valueOf(startingScore));

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            Button newButton = findViewById(id);

            final Button finalNewButton = newButton;
            newButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    doCheck(finalNewButton);
                }
            });
        }
        readyTimer();
    }


    @Override
    protected void onStart(){
        super.onStart();

    }


    private void doCheck(Button checkButton) {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        int currentScore = Integer.parseInt(score.getText().toString());
        if (checkButton.getText() == "*") {
            currentScore += 1;
            score.setText(String.valueOf(currentScore));
            Log.v(TAG, "Hit, score added!”");
        } else if (checkButton.getText() != "*" && currentScore > 0) {
            currentScore -= 1;
            score.setText(String.valueOf(currentScore));
            Log.v(TAG, "Missed, score deducted!”");
        } else {
            Log.v(TAG, "Error Occurred”");
        }
    }


    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        for(final int id : BUTTON_IDS){
            Button renewButton = findViewById(id);
            renewButton.setText("O");
        }


        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        int buttonId = (int) Array.get(BUTTON_IDS,randomLocation);
        Button setButton = findViewById(buttonId);
        setButton.setText("*");


    }

}