package com.ruthb.careapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ruthb.careapp.R
import com.ruthb.careapp.business.UserBusiness
import com.ruthb.careapp.entities.UserInfo
import com.ruthb.careapp.util.NumberMask
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private lateinit var mUserBusiness: UserBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mUserBusiness = UserBusiness(this)

        edtNumber.addTextChangedListener(NumberMask.insert("(##) # ####-####", edtNumber))

        btnSave.setOnClickListener {
            if (edtNumber.text.toString() != "") {
                if(sendInfo()){
                    this.finish()
                }
            }
        }

    }

    private fun sendInfo(): Boolean {
        val name = FirebaseAuth.getInstance().currentUser?.displayName
        val email = FirebaseAuth.getInstance().currentUser?.email
        val phone = NumberMask.unmask(edtNumber.text.toString())
        var work: Boolean = false

        try {
            mUserBusiness.sendInfo(UserInfo(username = name!!,
                    email = email!!,
                    phone = phone,
                    address = edtAddress.text.toString(),
                    neighborhood = edtNeighborhood.text.toString(),
                    city = edtCity.text.toString()))

            val toast = Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT)

            val toastLayout = layoutInflater.inflate(R.layout.toast_custom, null)
            toast.view = toastLayout
            val tvMessage = toast.view.findViewById<TextView>(R.id.tvMessage)
            tvMessage.text = "Cadastrado com sucesso!"
            toast.show()
            work = true
        } catch (e: Exception) {
            work = false
        }
        return work

    }
}
