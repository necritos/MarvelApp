package com.kodevian.marvelapp.view.auth

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by user on 20/11/17.
 */
class AuthPresenter(val mView:AuthContract.View, val mAuth:FirebaseAuth) : AuthContract.Presenter{


    override fun authUser(mail: String, password: String) {
        if(!mView.isLoading()){
            mView.setLoadingIndicator(true)
            mAuth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener {
                        task ->
                            if(task.isSuccessful){
                                val user = mAuth.currentUser!!
                                mView.successAuthUser(user)
                            } else{
                                mView.setError("Authentication fail")
                            }
                        mView.setLoadingIndicator(false)
                    }
        }
    }


}