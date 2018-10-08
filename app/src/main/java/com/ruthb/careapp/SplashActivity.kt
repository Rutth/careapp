package com.ruthb.careapp

import android.content.Intent
import com.daimajia.androidanimations.library.Techniques
import com.ruthb.careapp.constants.CareConstants
import com.ruthb.careapp.util.SecurityPreferences
import com.viksaa.sssplash.lib.activity.AwesomeSplash
import com.viksaa.sssplash.lib.cnst.Flags
import com.viksaa.sssplash.lib.model.ConfigSplash

class SplashActivity : AwesomeSplash() {

    private lateinit var mSecurityPreferences: SecurityPreferences


    override fun initSplash(configSplash: ConfigSplash) {

        mSecurityPreferences = SecurityPreferences(this)

        configSplash.setBackgroundColor(R.color.colorPrimaryDark)
        configSplash.setAnimCircularRevealDuration(1000)
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT)
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM)

        configSplash.setLogoSplash(R.drawable.logo_care)
        configSplash.setAnimLogoSplashDuration(1000)
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn)


        configSplash.setOriginalHeight(400)
        configSplash.setOriginalWidth(400)
        configSplash.setAnimPathStrokeDrawingDuration(3000)
        configSplash.setPathSplashStrokeSize(3)
        configSplash.setPathSplashStrokeColor(R.color.colorAccent)
        configSplash.setAnimPathFillingDuration(3000)
        configSplash.setPathSplashFillColor(R.color.colorPrimary)


        configSplash.setTitleSplash("Care");
        configSplash.setTitleTextColor(android.R.color.white);
        configSplash.setTitleTextSize(30f)
        configSplash.setAnimTitleDuration(3000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        configSplash.setTitleFont("fonts/lubalingraph.ttf");
    }

    override fun animationsFinished() {
        if(mSecurityPreferences.getStoredString(CareConstants.USER.USER_UID) != ""){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//    }
}
