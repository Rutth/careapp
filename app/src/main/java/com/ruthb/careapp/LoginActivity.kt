package com.ruthb.careapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ruthb.careapp.business.UserBusiness
import kotlinx.android.synthetic.main.activity_login.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.ruthb.careapp.repo.UserRepo


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserBusiness: UserBusiness
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val TAG = "LOGINACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mUserBusiness = UserBusiness(this)
        setListener()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

    }

    private fun setListener() {
        btnLoginGoogle.setOnClickListener(this)
        sign_in_google.setOnClickListener{
            handleLoginGoogle()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnLoginGoogle -> {
                sign_in_google.isPressed = true
            }
        }
    }

    private fun handleLoginGoogle(){
//        mUserBusiness.loginUser(edtEmail.text.toString(), edtPassword.text.toString())
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                mUserBusiness.loginGoogle(account)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
                Toast.makeText(this, "Erro de autenticação!", Toast.LENGTH_LONG).show()
            }

        }
    }

}
