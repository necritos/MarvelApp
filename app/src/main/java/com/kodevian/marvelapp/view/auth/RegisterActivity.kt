package com.kodevian.marvelapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.kodevian.marvelapp.R
import com.kodevian.marvelapp.base.BaseActivity
import com.kodevian.marvelapp.view.MainActivity

/**
 * Created by user on 20/11/17.
 */
class RegisterActivity : BaseActivity(), RegisterContract.View {


    var load: Boolean = false
    lateinit var mRegisterPresenter: RegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        showToolbar()
        val btn_register = findViewById<View>(R.id.btn_register) as Button
        val etMail = findViewById<View>(R.id.et_email) as EditText
        val etPassword = findViewById<View>(R.id.et_password) as EditText
        val etName = findViewById<View>(R.id.et_name) as EditText
        //presenter
        mRegisterPresenter = RegisterPresenter(this,
                FirebaseAuth.getInstance(),
                FirebaseDatabase.getInstance()
                )
        btn_register.setOnClickListener {
            mRegisterPresenter.registerUser(etName.text.toString(),
                    etMail.text.toString(),
                    etPassword.text.toString()
                    )
        }

    }

    private fun showToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true)
            ab.setDisplayShowHomeEnabled(true)
            ab.setTitle("Registrar")
        }

    }

    override fun successRegisterUser(user: FirebaseUser) {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun isLoading(): Boolean = load

    override fun setLoadingIndicator(visible: Boolean) {
        load = visible
    }

    override fun setError(msg: String) {
        showMessage(null, msg)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}