package com.kodevian.marvelapp.model



data class CharacterEntity(val id:Int, val name:String?, val description:String?, val thumbnail:Thumbnail, val urls:List<Url>)