package com.example.hangmangame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.hangmangame.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    lateinit var splashBinding : ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashBinding = ActivityWelcomeBinding.inflate(layoutInflater)
        val view = splashBinding.root
        setContentView(view)

        val hangAnimation = AnimationUtils.loadAnimation(applicationContext, androidx.appcompat.R.anim.abc_slide_in_top)
        splashBinding.textViewHang.startAnimation(hangAnimation)

        val manAnimation = AnimationUtils.loadAnimation(applicationContext, androidx.appcompat.R.anim.abc_tooltip_enter)
        splashBinding.textViewMan.startAnimation(manAnimation)

        val gameAnimation = AnimationUtils.loadAnimation(applicationContext, androidx.appcompat.R.anim.abc_slide_in_bottom)
        splashBinding.textViewGame.startAnimation(gameAnimation)

        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed(object : Runnable{
            override fun run() {
                val intent = Intent(this@WelcomeActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 5000)
    }
}