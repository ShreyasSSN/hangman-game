package com.example.hangmangame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangmangame.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var resultBinding :ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        val view = resultBinding.root
        setContentView(view)

        var score = intent.getIntExtra("score", 0)
            if (score != 0){
                resultBinding.textViewScore.text = "score : $score"
            }else{
                resultBinding.textViewScore.isVisible = false
                resultBinding.imageViewResult.setImageResource(R.drawable.loser)
            }

        resultBinding.buttonStartGame.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        resultBinding.buttonExit.setOnClickListener {
            finish()
        }
    }
}