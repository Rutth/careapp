package com.ruthb.careapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.ruthb.careapp.business.UserBusiness
import com.ruthb.careapp.entities.UserEntity
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mUserBusiness = UserBusiness(this)

        setListeners()
    }

    private fun setListeners() {
        btnSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSave -> {
                handleRegister()
            }
        }
    }

    private fun getFields(): UserEntity{
        val firstName = edtFirstName.text.toString()
        val lastName = edtLastName.text.toString()
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()

        return UserEntity(firstName, lastName, email, password)
    }

    private fun handleRegister(){
        try{
            mUserBusiness.registerUser(getFields())
        } catch (e: Exception){
            Toast.makeText(this, getString(R.string.verifique_conexao), Toast.LENGTH_LONG).show()
        } catch (e: NullPointerException){
            Toast.makeText(this, getString(R.string.preencha_campos), Toast.LENGTH_LONG).show()
        }
    }
}
