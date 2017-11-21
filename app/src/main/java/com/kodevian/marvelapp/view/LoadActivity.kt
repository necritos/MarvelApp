package com.kodevian.marvelapp.view

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.kodevian.marvelapp.R
import com.kodevian.marvelapp.base.BaseActivity
import com.kodevian.marvelapp.view.auth.LoginActivity

/**
 * Created by user on 20/11/17.
 */
class LoadActivity: BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        val mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser?.isAnonymous == false){
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }else{
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}