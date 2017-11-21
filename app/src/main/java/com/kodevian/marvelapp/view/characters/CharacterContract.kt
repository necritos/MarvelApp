package com.kodevian.marvelapp.view.characters

import com.kodevian.marvelapp.model.CharacterEntity


interface CharacterContract {
    interface View{
        fun renderCharacters(characters:List<CharacterEntity>, offset:Int, text:String)
        fun isLoading():Boolean
        fun setLoadingIndicator(visible:Boolean)
        fun setError(msg:String)
        fun setPresenter(presenter: Presenter)
    }

    interface Presenter {
        fun getCharacter(offset:Int =0, s:String = "")
        // fun getCharacter() // fun getCharacter(offset) //fun getCharacter(s) // 2
        fun start()
        fun stop()
    }
}
