package com.ruthb.careapp.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ruthb.careapp.business.UserBusiness
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.ruthb.careapp.R


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val TAG = "LOGINACTIVITY"
    var callbackManager: CallbackManager? = null
    var canGo: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)
        setListener()

        callbackManager = CallbackManager.Factory.create()
        login_button.setReadPermissions("email")


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    private fun setListener() {
        btnLoginGoogle.setOnClickListener(this)
        btnLoginFacebook.setOnClickListener(this)
        login_button.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLoginGoogle -> {
                handleLoginGoogle()
            }
            R.id.btnLoginFacebook -> {
                login_button.callOnClick()
            }
            R.id.login_button -> {
                loginFacebookCallBack()
            }
        }
    }

    private fun loginFacebookCallBack() {
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                canGo = mUserBusiness.loginFacebook(loginResult.accessToken)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Autenticação cancelada!", Toast.LENGTH_LONG).show()
            }

            override fun onError(exception: FacebookException) {
                Toast.makeText(applicationContext, "Autenticação cancelada!", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleLoginGoogle() {
//        mUserBusiness.loginUser(edtEmail.text.toString(), edtPassword.text.toString())
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            if (requestCode == 9001) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                    canGo = mUserBusiness.loginGoogle(account)

                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                    Toast.makeText(this, "Erro de autenticação!", Toast.LENGTH_LONG).show()
                }

            } else {
                callbackManager!!.onActivityResult(requestCode, resultCode, data)

            }
        } catch (e: Exception) {
            Log.w(TAG, "auth fail", e)
            Toast.makeText(this, "Erro de autenticação!", Toast.LENGTH_LONG).show()

        } finally {
            if (canGo) {
                runOnUiThread {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

            }

        }

    }

}
