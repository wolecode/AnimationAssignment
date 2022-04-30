package com.example.animationassignment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.animationassignment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }
    private fun setupView() {
        with(binding.animationButton) {
            val animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 200f)
                .apply {
                repeatCount = 1
                repeatMode = ObjectAnimator.REVERSE
            }
            setupAnimatorListener(this, animator)

            setOnClickListener {
                animator.start()
            }
        }
    }

    private fun setupAnimatorListener(view: View, animator: ObjectAnimator) {
        animator.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                //super.onAnimationStart(animation)
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                //super.onAnimationEnd(animation)
                view.isEnabled = true
                val intent = Intent(baseContext, MotionLayoutActivity::class.java)
                startActivity(intent)
            }

        })
    }
}