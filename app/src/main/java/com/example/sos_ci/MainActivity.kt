package com.example.sos_ci

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sos_ci.fragments.NumeroUrgenceFragment
import com.example.sos_ci.fragments.NumeroVertFragment
class MainActivity : AppCompatActivity() {

    private lateinit var table1: TextView
    private lateinit var table2: TextView
    private var selectedTabNumber = 1

    @SuppressLint("MissingInflatedId", "CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        table1 = findViewById(R.id.table1)
        table2 = findViewById(R.id.table2)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, NumeroVertFragment::class.java, null)
            .commit()

        table1.setOnClickListener {
            selectTab(1)
        }

        table2.setOnClickListener {
            selectTab(2)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    @SuppressLint("CommitTransaction")
    private fun selectTab(tabNumber: Int) {
        var selectedTextView: TextView? = null
        var nonSelectedTextView: TextView? = null

        if (tabNumber == 1) {
            selectedTextView = table1
            nonSelectedTextView = table2

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, NumeroVertFragment::class.java, null)
                .commit()

        } else if (tabNumber == 2) {
            selectedTextView = table2
            nonSelectedTextView = table1

            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, NumeroUrgenceFragment::class.java, null)
                .commit()
        }

        selectedTextView?.let {
            val slideTo = (tabNumber - selectedTabNumber) * it.width.toFloat()
            val translateAnimation = TranslateAnimation(0f, slideTo, 0f, 0f).apply {
                duration = 100
            }

            if (selectedTabNumber == 1) {
                table1.startAnimation(translateAnimation)
            } else if (selectedTabNumber == 2) {
                table2.startAnimation(translateAnimation)
            }

            translateAnimation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {}

                override fun onAnimationEnd(animation: Animation) {
                    selectedTextView.setBackgroundResource(R.drawable.round_back_white_100)
                    selectedTextView.setTypeface(null, Typeface.BOLD)
                    selectedTextView.setTextColor(Color.parseColor("#007566"))

                    nonSelectedTextView?.apply {
                        setBackgroundColor(resources.getColor(android.R.color.transparent))
                        setTextColor(Color.parseColor("#80FFFFFF"))
                    }
                }

                override fun onAnimationRepeat(animation: Animation) {}
            })

            selectedTabNumber = tabNumber
        }
    }
}
