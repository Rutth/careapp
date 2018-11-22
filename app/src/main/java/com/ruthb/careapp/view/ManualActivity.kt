package com.ruthb.careapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.ruthb.careapp.R
import kotlinx.android.synthetic.main.activity_manual.*


class ManualActivity : AppCompatActivity(), OnLoadCompleteListener {
    override fun loadComplete(nbPages: Int) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)

        pdfView.fromAsset("guia_pratico_cuidador.pdf").onLoad(this).load()

    }
}
