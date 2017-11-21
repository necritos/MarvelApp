package com.kodevian.marvelapp.view.characters

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.kodevian.marvelapp.R
import com.kodevian.marvelapp.base.BaseActivity
import com.kodevian.marvelapp.model.CharacterEntity

/**
 * Created by user on 13/11/17.
 */
class CharacterDetailActivity : BaseActivity()  {

    var entity: CharacterEntity?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)
        showToolbar()
        if(intent.extras!=null){
            val str = intent.extras.getString("character")
            entity = Gson().fromJson(str,CharacterEntity::class.java)
            val description:TextView = findViewById<View>(R.id.tv_character_description) as TextView
            val image:ImageView = findViewById<View>(R.id.img_back) as ImageView

            description.text = entity?.description ?: ""
            Glide.with(image.context)
                    .load(entity?.thumbnail?.getFullImage()?:"")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.logomarvel)
                    .error(R.drawable.logomarvel)
                    .into(image)
            val collapsingToolbar : CollapsingToolbarLayout = findViewById(R.id.collapsing)
            collapsingToolbar.title = entity?.name ?: ""


        }

    }

    private fun showToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true)
            ab.setDisplayShowHomeEnabled(true)
            ab.setTitle("Detail Characters")
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}