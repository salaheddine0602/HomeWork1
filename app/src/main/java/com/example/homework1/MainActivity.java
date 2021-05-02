package com.example.homework1;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LinkedList<Animals> myFarm;
    private LinkedList<Animals> farm;
    private Animals Winner ;
    private int level =1 ;
    private ImageButton [] Choices=new ImageButton[8]; //an array that will contain the buttons
    private int []Visibles ={2,3,4,6,8};//the array that has the number of arrays that should be visisble at each level
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Choices[0]=findViewById(R.id.Upper1);
        Choices[1]=findViewById(R.id.Lower1);
        Choices[2]=findViewById(R.id.Upper2);
        Choices[3]=findViewById(R.id.Lower2);
        Choices[4]=findViewById(R.id.Upper3);
        Choices[5]=findViewById(R.id.Lower3);
        Choices[6]=findViewById(R.id.Upper4);
        Choices[7]=findViewById(R.id.Lower4);


        setinvisible();
        myFarm=initiallizeAnimals();
        farm=createLevel();




    }


    public void PlayGame(View v)
    {
        TextView Pnts=findViewById(R.id.CurrentScore);

        int points=Integer.parseInt(Pnts.getText().toString());
        MediaPlayer status ;
        if (v.getId()==Winner.getPressed().getId())
        {
            status=MediaPlayer.create(this,R.raw.correct);
            points++;



        }
        else
        {
            status=MediaPlayer.create(this,R.raw.incorrect);
            level=0;
            points--;
            setinvisible();
            Log.d("incorrect",""+level);

        }
        Pnts.setText(String.valueOf(points));
        status.start();
        while (status.isPlaying())
        {
            ;
        }
        level++;
        farm=createLevel();


    }
    public void setinvisible()
    {
        for(int i=0;i<Choices.length;i++)
        {
            Choices[i].setVisibility(View.INVISIBLE);
        }
    }
    public LinkedList<Animals> createLevel()
    {

        LinkedList<Animals> possible=new LinkedList<Animals>() ;
        LinkedList<Animals> temp = (LinkedList<Animals>)myFarm.clone();
        Animals result ;
        int max,min=0 ,random_int;

        for (int i=0;i<Visibles[level-1];i++) {
            max = temp.size() - 1;
            random_int= (int)Math.floor(Math.random()*(max-0+1)+0);
            result = temp.remove(random_int);
            result.setPressed(Choices[i]);
            possible.add(result);
            Choices[i].setImageResource(result.getImage());
            Choices[i].setVisibility(View.VISIBLE);
        }


        ChooseWinner(possible);

        TextView screen =(TextView)findViewById(R.id.Animal);
        screen.setText(Winner.getName());

        MediaPlayer sound =MediaPlayer.create(this,Winner.getSound());
        sound.start();
        while(sound.isPlaying())
        {
            ;
        }

            return possible;
    }// function to return the array of all images


    public void ChooseWinner(LinkedList<Animals> Possible)
    {
        int random_int= (int)Math.floor(Math.random()*((Possible.size()-1)-0+1)+0);
        Winner=Possible.get(random_int);

    }




    private LinkedList<Animals> initiallizeAnimals()
    {
        LinkedList<Animals> myAnimals=new LinkedList<Animals>();
        myAnimals.add(new Animals(R.raw.bear,"Bear",R.drawable.bear));
        myAnimals.add(new Animals(R.raw.bee,"Bee",R.drawable.bee));
        myAnimals.add(new Animals(R.raw.camel,"Camel",R.drawable.camel));
        myAnimals.add(new Animals(R.raw.cat,"Cat",R.drawable.cat));
        myAnimals.add(new Animals(R.raw.cow,"Cow",R.drawable.cow));
        myAnimals.add(new Animals(R.raw.dog,"Dog",R.drawable.dog));
        myAnimals.add(new Animals(R.raw.dolphin,"Dolphin",R.drawable.dolphin));
        myAnimals.add(new Animals(R.raw.donkey,"Donkey",R.drawable.donkey));
        myAnimals.add(new Animals(R.raw.duck,"Duck",R.drawable.duck));
        myAnimals.add(new Animals(R.raw.eagle,"Eagle",R.drawable.eagle));
        myAnimals.add(new Animals(R.raw.elephant,"Elephant",R.drawable.elephant));
        myAnimals.add(new Animals(R.raw.frog,"Frog",R.drawable.frog));
        myAnimals.add(new Animals(R.raw.horse,"Horse",R.drawable.horse));
        myAnimals.add(new Animals(R.raw.lion,"Lion",R.drawable.lion));
        myAnimals.add(new Animals(R.raw.monkey,"Monkey",R.drawable.monkey));
        myAnimals.add(new Animals(R.raw.parrot,"Parrot",R.drawable.parrot));
        myAnimals.add(new Animals(R.raw.rat,"Rat",R.drawable.rat));
        myAnimals.add(new Animals(R.raw.rooster,"Rooster",R.drawable.rooster));
        myAnimals.add(new Animals(R.raw.sheep,"Sheep",R.drawable.sheep));
        myAnimals.add(new Animals(R.raw.snake,"Snake",R.drawable.snake));
        myAnimals.add(new Animals(R.raw.wolf,"Wolf",R.drawable.wolf));
        myAnimals.add( new Animals(R.raw.chicken,"Chicken",R.drawable.chicken));
        myAnimals.add(new Animals(R.raw.owl,"Owl",R.drawable.owl));

        return myAnimals;

    }
}
class Animals
{
    private int sound ;
    private String name;
    private int image ;
    ImageButton pressed=null;
    public Animals(int sound,String name,int image)
    {
        this.sound=sound;
        this.name=name;
        this.image=image;

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

    if (pressed==btn)
    {
        return true;
    }
    else
        return false;
    }

    @Override
    public String toString() {
        return "Animals{" +
                "name='" + name + '\'' +
                '}';
    }
}