package com.kodevian.marvelapp.view.auth

import com.google.firebase.auth.FirebaseUser

/**
 * Created by user on 20/11/17.
 */
interface RegisterContract {
    interface View{
        fun successRegisterUser(user: FirebaseUser)
        fun isLoading():Boolean
        fun setLoadingIndicator(visible:Boolean)
        fun setError(msg:String)
    }

    interface Presenter{
        fun registerUser(name:String, mail:String, password:String)
    }

}