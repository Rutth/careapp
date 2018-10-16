package com.ruthb.careapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.opengl.ETC1.getHeight
import android.view.View
import android.view.animation.TranslateAnimation
import kotlinx.android.synthetic.main.activity_patient.*


class PatientActivity : AppCompatActivity(), View.OnClickListener {

    private var isUp: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient)

        addPatient.setOnClickListener(this)
        isUp = false
    }

    override fun onClick(v: View) {
        if (isUp) {
            slideDown(modal)

        } else {
            slideUp(modal)

        }
        isUp = !isUp
    }

    private fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
                0f,
                0f,
                view.height.toFloat(),
                0f)
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    private fun slideDown(view: View) {
        val animate = TranslateAnimation(
                0f,
                0f,
                0f,
                view.height.toFloat())
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }
}
