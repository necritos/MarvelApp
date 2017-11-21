package com.kodevian.marvelapp.view.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kodevian.marvelapp.model.UserEntity

/**
 * Created by user on 20/11/17.
 */
class RegisterPresenter(val mView: RegisterContract.View,
                        val mAuth: FirebaseAuth,
                        val mDatabase: FirebaseDatabase) : RegisterContract.Presenter {


    override fun registerUser(name:String, mail: String, password: String) {
        if(!mView.isLoading()){
            mView.setLoadingIndicator(true)
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener {
                        task ->
                            if(task.isSuccessful){
                                val user = mAuth.currentUser!!
                                writeUser(userId = user.uid, name = name, email = mail)
                                mView.successRegisterUser(user)
                            } else{
                                mView.setError("Create account fail")
                            }
                        mView.setLoadingIndicator(false)
                    }
        }
    }

    private fun writeUser(userId:String, name:String, email:String){
        val user = UserEntity(name, email)
        val reference : DatabaseReference = mDatabase.reference
        reference.child("users").child(userId).setValue(user)

    }


}