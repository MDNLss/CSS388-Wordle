package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.util.Log
import android.view.View
import android.widget.Toast

/**
 * Parameters / Fields:
 *   wordToGuess : String - the target word the user is trying to guess
 *   guess : String - what the user entered as their guess
 *
 * Returns a String of 'O', '+', and 'X', where:
 *   'O' represents the right letter in the right place
 *   '+' represents the right letter in the wrong place
 *   'X' represents a letter not in the target word
 */
private fun checkGuess(guess: String,wordToGuess:String) : String {
    var result = ""
    for (i in 0..3) {
        if (guess[i] == wordToGuess[i]) {
            result += "O"
        }
        else if (guess[i] in wordToGuess) {
            result += "+"
        }
        else {
            result += "X"
        }
    }
    return result
}


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val answer_word = FourLetterWordList.FourLetterWordList.getRandomFourLetterWord()
        Log.v("ans:","answer_word "+answer_word);
        var guess: String = "" // user guess
        var guess_checked: String = "";
        val guess_btn: Button = findViewById<Button>(R.id.guess_btn)
        val answer_view = findViewById<EditText>(R.id.answer_id);
        var vis_counter = 0; //counter for elements to be made visibile
        var textArray: ArrayList<TextView> = ArrayList<TextView>();
        textArray.add(findViewById<Button>(R.id.textView0))
        textArray.add(findViewById<Button>(R.id.textView1))
        textArray.add(findViewById<Button>(R.id.textView2))
        textArray.add(findViewById<Button>(R.id.textView3))
        textArray.add(findViewById<Button>(R.id.textView4))
        textArray.add(findViewById<Button>(R.id.textView5))
        var boxArray:ArrayList<TextView> = ArrayList<TextView>();
        boxArray.add(findViewById<Button>(R.id.box0))
        boxArray.add(findViewById<Button>(R.id.box1))
        boxArray.add(findViewById<Button>(R.id.box2))
        boxArray.add(findViewById<Button>(R.id.box3))
        boxArray.add(findViewById<Button>(R.id.box4))
        boxArray.add(findViewById<Button>(R.id.box5))
        //set all guesses to invisible

        for (i in 0 until textArray.size) {
            textArray.get(i).visibility = View.INVISIBLE
        }
        for (i in 0 until boxArray.size ) {
           boxArray.get(i).visibility = View.INVISIBLE
        }
        var current_label: TextView = TextView(this);
        var current_check: TextView = TextView(this);
        guess_btn.setOnClickListener {
            if (vis_counter >= 5) {
                // set counter to 0 and make labels invisible
                vis_counter = 0
                /*
                for (i in 0 until boxArray.size) {
                    boxArray.get(i).visibility = View.INVISIBLE
                    textArray.get(i).visibility = View.INVISIBLE
                } */
                Toast.makeText(this,"Exceeded number of guesses",Toast.LENGTH_LONG).show()
                guess_btn.setAlpha(.5f);
                guess_btn.setClickable(false);

            }  else {
                guess = answer_view.getText().toString()
                guess_checked = checkGuess(guess.uppercase(),answer_word)
                Log.v("guess:","guess is:"+guess+"ans is: "+answer_word)
                if (guess == answer_word) {
                    Toast.makeText(this,"Correct",Toast.LENGTH_LONG).show()
                }
                current_label = textArray.get(vis_counter)
                current_check = textArray.get(vis_counter+1)
                current_label.visibility = View.VISIBLE
                current_check.visibility = View.VISIBLE

                boxArray.get(vis_counter).visibility = View.VISIBLE
                boxArray.get(vis_counter+1).visibility = View.VISIBLE
                boxArray.get(vis_counter).setText(guess)
                boxArray.get(vis_counter+1).setText(guess_checked)
                //set text labels on right
                vis_counter += 2
            }
        }
    }
}