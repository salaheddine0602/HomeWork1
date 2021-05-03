package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private static final String SAVED_HIGH_SCORE_INT = "high_score";
    private LinkedList<Animals> myFarm; //contains all the animals
    private LinkedList<Animals> myLevel; // contains all the animals in the current level
    private Animals Winner; //the Winner of each level
    private int level = 1;
    int levelEnter = 0;
    private TextToSpeech Mtts;
    MediaPlayer welcome;
    private ImageButton[] Choices = new ImageButton[8]; //an array that will contain the buttons
    private int[] Visibles = {2, 3, 4, 6, 8};//the array that has the number of arrays that should be visisble at each level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        welcome =MediaPlayer.create(this, R.raw.backgroundusic);
        welcome.start();




        //start the game






    }
    public void open(View view)
    {
        welcome.stop();
        setContentView(R.layout.activity_main);
        //find all the 8 buttons i already have in my interface.
        Choices[0] = findViewById(R.id.Upper1);
        Choices[1] = findViewById(R.id.Lower1);
        Choices[2] = findViewById(R.id.Upper2);
        Choices[3] = findViewById(R.id.Lower2);
        Choices[4] = findViewById(R.id.Upper3);
        Choices[5] = findViewById(R.id.Lower3);
        Choices[6] = findViewById(R.id.Upper4);
        Choices[7] = findViewById(R.id.Lower4);

        myFarm = initiallizeAnimals();
        myLevel = createLevel();
        SetEnablability(true);
        //get the all time high score
        int high = getSavedHighScore();
        TextView Hi = findViewById(R.id.HighestScore);
        Hi.setText(String.valueOf(high));

    }
    // Initiallizing all the animals with their sounds,names,and images

    /************************************************************/
    private LinkedList<Animals> initiallizeAnimals() {
        LinkedList<Animals> myAnimals = new LinkedList<Animals>();
        myAnimals.add(new Animals(R.raw.bear, "Bear", R.drawable.bear));
        myAnimals.add(new Animals(R.raw.bee, "Bee", R.drawable.bee));
        myAnimals.add(new Animals(R.raw.camel, "Camel", R.drawable.camel));
        myAnimals.add(new Animals(R.raw.cat, "Cat", R.drawable.cat));
        myAnimals.add(new Animals(R.raw.cow, "Cow", R.drawable.cow));
        myAnimals.add(new Animals(R.raw.dog, "Dog", R.drawable.dog));
        myAnimals.add(new Animals(R.raw.dolphin, "Dolphin", R.drawable.dolphin));
        myAnimals.add(new Animals(R.raw.donkey, "Donkey", R.drawable.donkey));
        myAnimals.add(new Animals(R.raw.duck, "Duck", R.drawable.duck));
        myAnimals.add(new Animals(R.raw.eagle, "Eagle", R.drawable.eagle));
        myAnimals.add(new Animals(R.raw.elephant, "Elephant", R.drawable.elephant));
        myAnimals.add(new Animals(R.raw.frog, "Frog", R.drawable.frog));
        myAnimals.add(new Animals(R.raw.horse, "Horse", R.drawable.horse));
        myAnimals.add(new Animals(R.raw.lion, "Lion", R.drawable.lion));
        myAnimals.add(new Animals(R.raw.monkey, "Monkey", R.drawable.monkey));
        myAnimals.add(new Animals(R.raw.parrot, "Parrot", R.drawable.parrot));
        myAnimals.add(new Animals(R.raw.rat, "Rat", R.drawable.rat));
        myAnimals.add(new Animals(R.raw.rooster, "Rooster", R.drawable.rooster));
        myAnimals.add(new Animals(R.raw.sheep, "Sheep", R.drawable.sheep));
        myAnimals.add(new Animals(R.raw.snake, "Snake", R.drawable.snake));
        myAnimals.add(new Animals(R.raw.wolf, "Wolf", R.drawable.wolf));
        myAnimals.add(new Animals(R.raw.chicken, "Chicken", R.drawable.chicken));
        myAnimals.add(new Animals(R.raw.owl, "Owl", R.drawable.owl));

        return myAnimals;

    }

    //Set all button to invisible

    /************************************************************/

    private void SetInVisible() {
        for (int i = 0; i < Choices.length; i++) {
            Choices[i].setVisibility(View.INVISIBLE);
        }
    }
    //Set all button to Disable

    /************************************************************/

    private void SetEnablability(Boolean state) {
        for (int i = 0; i < Choices.length; i++) {
            Choices[i].setEnabled(state);
        }
    }
    //choose the winnner animal

    /************************************************************/
    private void ChooseWinner(LinkedList<Animals> Possible) {
        int random_int = (int) Math.floor(Math.random() * ((Possible.size() - 1) - 0 + 1) + 0);
        Winner = Possible.get(random_int);
        //delete the winner animal to be sure that it will only come once
        myFarm.remove(Winner);

    }

    //create the level

    /************************************************************/
    private LinkedList<Animals> createLevel() {
        //i used Linked list for their efficiency in deletingelement
        //i used that property to guarantee that an animal never repeat itself


        //Linked list to save the available choices in current level
        LinkedList<Animals> possible = new LinkedList<Animals>();
        //a temporary List to pick Random animals from
        LinkedList<Animals> temp = (LinkedList<Animals>) myFarm.clone();
        //a randomly chosen animal
        Animals result;

        if (level == 6) {
            //if levels are finished end game
            Toast.makeText(this, "No more Levels", Toast.LENGTH_LONG);
            return null;

        }
        int max, min = 0, random_int;

        for (int i = 0; i < Visibles[level - 1]; i++) {
            max = temp.size() - 1;
            random_int = (int) Math.floor(Math.random() * (max - 0 + 1) + 0);
            //used remove to insure animals will not repear in the same level choices
            result = temp.remove(random_int);
            //set the buttton that has that animal in it
            result.setPressed(Choices[i]);
            possible.add(result);
            Choices[i].setImageResource(result.getImage());
            Choices[i].setVisibility(View.VISIBLE);
        }

        Toast.makeText(this, "Level " + level, Toast.LENGTH_SHORT).show();
        //send the possible choices to randomly pick one of them
        ChooseWinner(possible);

        TextView screen = (TextView) findViewById(R.id.Animal);
        screen.setText(Winner.getName());
        ReadName(Winner.getName());
        while (Mtts.isSpeaking()) ;
        if(!Mtts.isSpeaking())
         {
             MediaPlayer sound = MediaPlayer.create(this, Winner.getSound());
            sound.start();
        }




        return possible;
    }
    //Game player

    /************************************************************/
    public void PlayGame(View v) {
        //Points Textview
        TextView Pnts = findViewById(R.id.CurrentScore);
        //Highest score
        TextView Hi = findViewById(R.id.HighestScore);
        //Points
        int points = Integer.parseInt(Pnts.getText().toString());
        MediaPlayer status;

        int High = Integer.parseInt(Hi.getText().toString());

        //check if the user pressed the right button
        if (v.getId() == Winner.getPressed().getId()) {
            status = MediaPlayer.create(this, R.raw.correct);
            levelEnter++;
            points++;
            if (points > High) {
                High = points;
                saveHighScore(High);
                Hi.setText(String.valueOf(points));

            }


        } else {
            status = MediaPlayer.create(this, R.raw.incorrect);
            level = 1;
            points = 0;
            SetInVisible();
            Log.d("incorrect", "" + level);

        }
        Pnts.setText(String.valueOf(points));

        status.start();
        while (status.isPlaying()) {
            ;
        }

        if(levelEnter==3)
        {
            level++;
            levelEnter=0;
        }
        myLevel = createLevel();
        if (myLevel == null) {
            SetInVisible();
            SetEnablability(false);
            welcome =MediaPlayer.create(this, R.raw.backgroundusic);
            welcome.start();
            setContentView(R.layout.menu);

        }


    }



    private void ReadName(String name) {
        Mtts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    // replace this Locale with whatever you want
                    Locale localeToUse = new Locale("en","UK");
                    Mtts.setLanguage(localeToUse);
                    Mtts.setPitch(2);
                    Mtts.setPitch(1);
                    Mtts.speak(name, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });



    }


    //save High Score

    /************************************************************/
    private void saveHighScore(int highScore) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor spe = sp.edit();
        spe.putInt(SAVED_HIGH_SCORE_INT, highScore);
        spe.apply();
    }

    //get High Score

    /************************************************************/
    private int getSavedHighScore() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        return sp.getInt(SAVED_HIGH_SCORE_INT, -1);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (Mtts != null) {
            Mtts.stop();
            Mtts.shutdown();

        }


    }

}
















//create a class that contains all the animals cards ,and each animal has a
//an Image and a sound and a name
//i used the oop for more efficient way of using the animals
class Animals {
    private int sound;
    private String name;
    private int image;
    ImageButton pressed = null;

    public Animals(int sound, String name, int image) {
        this.sound = sound;
        this.name = name;
        this.image = image;

    }

    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getSound() {
        return sound;
    }

    public void setPressed(ImageButton pressed) {
        this.pressed = pressed;
    }

    public ImageButton getPressed() {
        return pressed;
    }


    public boolean FindButton(ImageButton btn) {

        if (pressed == btn) {
            return true;
        } else
            return false;
    }

    @Override
    public String toString() {
        return "Animals{" +
                "name='" + name + '\'' +
                '}';
    }
}