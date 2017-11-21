package com.kodevian.marvelapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kodevian.marvelapp.R
import com.kodevian.marvelapp.base.BaseActivity
import com.kodevian.marvelapp.view.MainActivity

/**
 * Created by user on 20/11/17.
 */
class LoginActivity : BaseActivity(), AuthContract.View {

    var load: Boolean = false
    lateinit var authPresenter: AuthPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        showToolbar()
        val btn_login = findViewById<View>(R.id.btn_login) as Button
        val btn_register = findViewById<View>(R.id.btn_register) as Button
        val etMail = findViewById<View>(R.id.et_email) as EditText
        val etPassword = findViewById<View>(R.id.et_password) as EditText
        //presenter
        authPresenter = AuthPresenter(this, FirebaseAuth.getInstance())

        btn_login.setOnClickListener {
            authPresenter.authUser(etMail.text.toString(), etPassword.text.toString())
        }
        btn_register.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }

    }

    private fun showToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true)
            ab.setDisplayShowHomeEnabled(true)
            ab.setTitle("Login")
        }

    }

    override fun successAuthUser(user: FirebaseUser) {
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
}