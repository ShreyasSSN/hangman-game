package com.example.hangmangame

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.hangmangame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var lengthOfDictionary : Int = WordData.hangmanDictionaryLength
    private var questionNumber: Int = 0
    private var buttonList = listOf<Button>()
    private var question : String = ""
    private var hint : String = ""
    private var questionView = ""
    private var imageList = listOf(R.drawable.image1, R.drawable.image2,R.drawable.image3,
        R.drawable.image4,R.drawable.image5,R.drawable.image6,R.drawable.image7)
    private var imageNumber = 0
    private var score = 0
    lateinit var shapeDrawable : GradientDrawable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        buttonList = ('A'..'Z').map { char ->
            val buttonId = view.resources.getIdentifier("button$char", "id", mainBinding.root.context.packageName)
            view.findViewById(buttonId)
        }

        startGame()

        buttonList.forEach { button ->
            button.setOnClickListener {
                buttonClick(button, button.text.toString())
            }
        }

        mainBinding.buttonHint.setOnClickListener {
            showHintDialog()
        }

        mainBinding.floatingActionButton.setOnClickListener {
            showHowToPlayDialog()
        }
    }

    private fun startGame() {
        mainBinding.textView.text = ""
        questionNumber = (0..lengthOfDictionary).random()
        question = WordData.hangmanList[questionNumber].key.uppercase()
        hint = WordData.hangmanList[questionNumber].value
        question.forEach { letter ->
            questionView += if(letter != ' '){
                '_'
            }else{
                ' '
            }
        }
        buttonList.forEach {button ->
            button.isClickable = true
            shapeDrawable = button.background as GradientDrawable
            shapeDrawable.setColor(Color.GRAY)

        }
        mainBinding.textView.text = questionView
    }

    private fun buttonClick(button: Button, letter : String){

        shapeDrawable = button.background as GradientDrawable
        shapeDrawable.setColor(androidx.appcompat.R.attr.colorPrimary)

        button.isClickable = false
        println(question)
        if(letter in question){
            for(indexOfLetter in question.indices){
                if (question[indexOfLetter] == letter.single()){
                    questionView = replaceCharAtIndex(questionView, indexOfLetter, letter.single())
                }
            }
            mainBinding.textView.text = questionView
            if ('_' !in questionView){
                score++
                questionView = ""
                showAlertDialog()
            }
        }else{
            if (imageNumber < 6){
                mainBinding.imageViewHangman.setImageResource(imageList[imageNumber])
                imageNumber++
            }else{
                mainBinding.imageViewHangman.setImageResource(imageList[6])
                moveToResultActivity()
            }
        }

    }

    private fun showAlertDialog() {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("Correct !")
            .setMessage("$question is the right answer")
            .setIcon(R.drawable.alert_icon)
            .setNegativeButton("Results") { _, _ ->
                moveToResultActivity()
            }
            .setPositiveButton("One More") { _, _ ->
                startGame()
            }
        alertDialog.create().show()
    }

    private fun moveToResultActivity(){

        val intent = Intent(this@MainActivity, ResultActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
        finish()

    }

    private fun showHintDialog() {
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("Hint")
            .setMessage(hint)
            .setIcon(R.drawable.hint_icon)
            .setPositiveButton("Ok") { _, _ ->

            }
        alertDialog.create().show()
    }

    private fun showHowToPlayDialog() {

        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setTitle("How To Play")
            .setIcon(R.drawable.how_to_play)
            .setPositiveButton("Got it") { _, _ ->
            }
        val customDialogLayout = layoutInflater.inflate(R.layout.dialog_box,null)
        alertDialog.setView(customDialogLayout)
        alertDialog.create().show()
    }

    private fun replaceCharAtIndex(question: String, indexOfLetter: Int, letter: Char) : String{
        val stringBuilder = StringBuilder(question)
        stringBuilder.setCharAt(indexOfLetter, letter)
        return stringBuilder.toString()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        moveToResultActivity()
    }
}