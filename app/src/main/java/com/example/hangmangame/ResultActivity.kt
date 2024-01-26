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

        var score = intent.getStringExtra("score")
        if (score != null) {
            if (score.toInt() > 0){
                resultBinding.textViewScore.text = "score : " + score.toString()
            }else{
                resultBinding.imageViewResult.setImageResource(R.drawable.loser)
            }
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