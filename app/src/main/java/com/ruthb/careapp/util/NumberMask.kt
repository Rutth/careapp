package com.ruthb.careapp.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class NumberMask {
    companion object {
        fun unmask(s: String): String {
            return s.replace("(", "").replace(")", "").replace("-", "").replace(" ", "")
        }

        fun insert(mask: String, ediTxt: EditText): TextWatcher {
            return object : TextWatcher {
                internal var isUpdating: Boolean = false
                internal var old = ""

                override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                           count: Int) {
                    val str = NumberMask.unmask(s.toString())
                    var mascara = ""
                    if (isUpdating) {
                        old = str
                        isUpdating = false
                        return
                    }
                    var i = 0
                    for (m in mask.toCharArray()) {
                        if (m != '#' && str.length > old.length) {
                            mascara += m
                            continue
                        }
                        try {
                            mascara += str.get(i)
                        } catch (e: Exception) {
                            break
                        }

                        i++
                    }
                    isUpdating = true
                    ediTxt.setText(mascara)
                    println("sendo digitado: " + ediTxt.text.toString())
                    println("Tamanho: " + mascara.length)
                    ediTxt.setSelection(mascara.length)


                }

                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                               after: Int) {
                }

                override fun afterTextChanged(s: Editable) {

                }
            }
        }
    }

}