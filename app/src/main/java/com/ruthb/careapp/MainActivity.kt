package com.ruthb.careapp

import android.Manifest
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ruthb.careapp.business.UserBusiness
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.helper.CircleTransform
import com.ruthb.careapp.util.SecurityPreferences
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import android.R.attr.y
import android.R.attr.x
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast



class MainActivity : AppCompatActivity(), View.OnClickListener, SensorEventListener {

    private lateinit var mSecurityPreferences: SecurityPreferences
    private lateinit var mUserBusiness: UserBusiness
    private lateinit var sensorMgr: SensorManager
    private var mLastShakeTime: Long = 0
    private var MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000
    var over: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSecurityPreferences = SecurityPreferences(this)
        mUserBusiness = UserBusiness(this)

        sensorMgr = getSystemService(SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        if (accelerometer != null) {
            sensorMgr.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }

        //mUserBusiness.userInfo()
        println("INFO: ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO)}")
        if (mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID) == "") {
            println("to dentro do if")
            over = mUserBusiness.getUser()
            if (over) setInfo() else recreate()
        } else {
            setInfo()
        }

        setListeners()
        println("INFO: ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)} - ${mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO)}")
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type === Sensor.TYPE_ACCELEROMETER) {
            val curTime = System.currentTimeMillis()
            if (curTime - mLastShakeTime > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

                val x = event.values[0]
                val y = event.values[1]
                val z = event.values[2]

                val acceleration = Math.sqrt(Math.pow(x.toDouble(), 2.0) +
                        Math.pow(y.toDouble(), 2.0) +
                        Math.pow(z.toDouble(), 2.0)) - SensorManager.GRAVITY_EARTH
                Log.d("MAIN", "Acceleration is " + acceleration + "m/s^2")

                if (acceleration > 3.25f) {
                    mLastShakeTime = curTime
                    Toast.makeText(this@MainActivity, "Shake", Toast.LENGTH_SHORT).show()
                    if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CALL_PHONE),1)
                    }
                    else
                    {
                        startActivity(Intent(Intent.ACTION_DIAL).setData(Uri.parse("tel:190")))
                    }

                }
            }
        }
    }

    private fun setInfo() {
        tvNome.text = mSecurityPreferences.getStoredString(CareConstants.USER.USER_NAME)
        tvEmail.text = mSecurityPreferences.getStoredString(CareConstants.USER.USER_EMAIL)

        Picasso.get()
                .load(mSecurityPreferences.getStoredString(CareConstants.USER.USER_PHOTO))
                .transform(CircleTransform())
                .placeholder(R.drawable.ic_user_placeholder)
                .error(R.drawable.ic_user_placeholder)
                .into(imgUser)
    }

    private fun setListeners() {
        logout.setOnClickListener(this)
        manual.setOnClickListener(this)
        addInfo.setOnClickListener(this)
        patient.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logout -> {
                mUserBusiness.logout(mSecurityPreferences.getStoredString(CareConstants.USER.USER_TYPE))
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            R.id.manual -> {
                startActivity(Intent(this, ManualActivity::class.java))
            }
            R.id.patient -> {
                startActivity(Intent(this, PatientActivity::class.java))
            }

        }
    }

}
