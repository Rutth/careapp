package com.ruthb.careapp.entities

data class UserInfo (var username: String, var email: String, var phone: String = "", var address: String = "", var neighborhood: String = "", var city: String = "")