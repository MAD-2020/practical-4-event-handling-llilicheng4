package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final String TAG = "Whack-a-Mole";
    Button button1;
    Button button2;
    Button button3;
    TextView score;
    int point = 0;
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2. *DONE*
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly. *DONE*
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide. *DONE*
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No. *DONE*
        - The function nextLevel() launches the new advanced page. *DONE*
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        score = findViewById(R.id.textView2);
        Log.v(TAG, "Finished Pre-Initialisation!");

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck(button1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck(button2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doCheck(button3);
            }
        });




    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().
         */

        if(checkButton.getText() == "*"){
            point+=1;
            score.setText("" + point);
            Log.v(TAG,"Hit, score added!”");
        }
        else if (checkButton.getText()!= "*" && point > 0){
            point-=1;
            score.setText("" + point);
            Log.v(TAG,"Missed, score deducted!”");
        }
        else{
            Log.v(TAG,"Error Occurred”");
        }
        setNewMole();

        if(point%10 == 0){
            //trigger dialogue box
            nextLevelQuery();

        }
    }

    private void nextLevelQuery(){
        /*
        Builds dialog box here.

        belongs here*/
        Log.v(TAG, "Advance option given to user!");
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-a-Mole incoming!");
        builder.setMessage("would you like to advance to the advanced mode?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");

                nextLevel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
                onResume();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }

    private void nextLevel(){
        Log.v(TAG, String.valueOf(point));
        /* Launch advanced page */
        Intent advancedLevel = new Intent(MainActivity.this, Main2Activity.class);

        advancedLevel.putExtra("Score", String.valueOf(point));

        startActivity(advancedLevel);
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        if(randomLocation == 1){
            button1.setText("*");
            button2.setText("O");
            button3.setText("O");
        }
        else if(randomLocation == 2)
        {
            button1.setText("O");
            button2.setText("*");
            button3.setText("O");
        }
        else{
            button1.setText("O");
            button2.setText("O");
            button3.setText("*");
        }

    }
}