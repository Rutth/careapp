package com.ruthb.careapp.entities

import java.io.Serializable

data class PatientEntity(var key: String = "",
                         var name: String = "",
                         var age: Int =  0,
                         var phone: String = "",
                         var address: String = "",
                         var neighborhood: String = "",
                         var city: String = "",
                         var gender: String = ""): Serializable