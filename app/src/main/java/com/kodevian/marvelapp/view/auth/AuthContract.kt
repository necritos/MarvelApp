package com.kodevian.marvelapp.view.auth

import com.google.firebase.auth.FirebaseUser

/**
 * Created by user on 20/11/17.
 */
interface AuthContract {
    interface View{
        fun successAuthUser(user: FirebaseUser)
        fun isLoading():Boolean
        fun setLoadingIndicator(visible:Boolean)
        fun setError(msg:String)
    }

    interface Presenter{
        fun authUser(mail:String, password:String)
    }

}