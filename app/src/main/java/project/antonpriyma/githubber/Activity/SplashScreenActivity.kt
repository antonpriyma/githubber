

package com.antonpriyma.githubber.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AlphaAnimation
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_splash_screen.*
import com.antonpriyma.githubber.R

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firebaseAuth =  FirebaseAuth.getInstance()

        val SPLASH_TIME_OUT = 3000

        Handler().postDelayed({



            if (firebaseAuth.currentUser!=null) {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            else{
                startActivity(Intent(this,LoginActivity::class.java))
            }

            finish()
        }, 2500)


        val alphaAnimation = AlphaAnimation(0.2f,1.0f)
        val alphaAnimation2 = AlphaAnimation(0.0f,1.0f)
        alphaAnimation.startOffset = 200
        alphaAnimation.duration = 800
        icon.animation = alphaAnimation

        alphaAnimation2.startOffset = 900
        alphaAnimation2.duration = 800
        text.animation = alphaAnimation2

    }
}
