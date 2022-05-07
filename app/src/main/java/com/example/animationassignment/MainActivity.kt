package com.example.animationassignment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.animationassignment.databinding.ActivityMainBinding
import kotlin.properties.Delegates
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var number by Delegates.notNull<Int>()
    var randomNumber = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
    }

    private fun setupView() {
        with(binding) {
            val animator = ObjectAnimator.ofFloat(animationButton, View.TRANSLATION_X, 200f)
                .apply {
                repeatCount = 1
                repeatMode = ObjectAnimator.REVERSE
            }
            val dieAnimator = ObjectAnimator.ofFloat(dieFace, View.ROTATION, -360f, 0f)
            setupAnimatorListener(animationButton, animator)
            setupAnimatorListener(dieFace, dieAnimator)

            animationButton.setOnClickListener {
                //animator.start()
                number = Integer.parseInt(luckyNumber.text.toString())
                if( number == 1 || number <= 6) {
                    dieAnimator.start()
                } else {
                    Toast.makeText(baseContext, "Number out of range", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    private fun setupAnimatorListener(view: View, animator: ObjectAnimator) {
        if (animator.propertyName!!.equals("ROTATION", ignoreCase = true)) {

            animator.addListener(object: AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                 randomNumber = Random.nextInt(1, 7)
                }

                override fun onAnimationEnd(animation: Animator?) {
                    when(randomNumber) {
                        1 -> binding.dieFace.setImageResource(R.drawable.die_face_1_t)
                        2 -> binding.dieFace.setImageResource(R.drawable.die_face_2_t)
                        3 -> binding.dieFace.setImageResource(R.drawable.die_face_3_t)
                        4 -> binding.dieFace.setImageResource(R.drawable.die_face_4_t)
                        5 -> binding.dieFace.setImageResource(R.drawable.die_face_5_t)
                        else -> binding.dieFace.setImageResource(R.drawable.die_face_6_t)
                    }

                    if (number == randomNumber) {
                        Toast.makeText(baseContext, "Wow! You guessed right", Toast.LENGTH_LONG).show()
                        val intent = Intent(baseContext, MotionLayoutActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(baseContext, "Oops! Try again", Toast.LENGTH_LONG).show()
                        binding.luckyNumber.text.clear()
                    }
                }
            })
            } else {
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
}