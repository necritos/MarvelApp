package com.kodevian.marvelapp.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.kodevian.marvelapp.R
import com.kodevian.marvelapp.base.BaseActivity
import com.kodevian.marvelapp.model.CharacterEntity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity(), CharacterContract.View {


    lateinit var rvCharacters:RecyclerView
    lateinit var characterAdapter : CharacterAdapter
    var characterPresenter : CharacterContract.Presenter ? =null
    var loading:Boolean = false
    var offset: Int = 0
    var mText: String = ""
    var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showToolbar()

        rvCharacters = findViewById<View>(R.id.rv_characters) as RecyclerView
        //val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val layoutManager = GridLayoutManager(this, 3)
        rvCharacters.layoutManager = layoutManager
        characterAdapter = CharacterAdapter(mutableListOf()){
            character ->
                val i:Intent = Intent(this, CharacterDetailActivity::class.java)
                var strObject: String = Gson().toJson(character)
                i.putExtra("character", strObject)
                startActivity(i)
        }
        rvCharacters.adapter = characterAdapter
        characterPresenter = CharactersPresenter(this)
        characterPresenter?.start()
        characterPresenter?.getCharacter(offset)
        rvCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0)
                //check for scroll down
                {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()

                    if (!loading ) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            characterPresenter?.getCharacter(offset,mText)
                        }
                    }

                }
            }
        })


        if (savedInstanceState != null) {
            // Restore value of members from saved state
            offset = savedInstanceState.getInt("OFFSET")
            mText = savedInstanceState.getString("TEXT")
            characterPresenter = CharactersPresenter(this)
        }

    }

    private fun showToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val ab = supportActionBar
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false)
            ab.setDisplayShowHomeEnabled(false)
            ab.setTitle("Marvel Characters")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        val iSearch = menu.findItem(R.id.action_search)
        val vSearch = iSearch.actionView as SearchView

        disposable = Observable.create(ObservableOnSubscribe<String> {
            subscriber ->
            vSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (!vSearch.isIconified) {
                        vSearch.isIconified = true
                    }
                    iSearch.collapseActionView()
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    subscriber.onNext(newText)
                    return true
                }
            })
        }).debounce(500,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    text ->
                    characterAdapter.clear()
                    characterPresenter?.getCharacter(s=text)
                }


        return super.onCreateOptionsMenu(menu)
    }



    override fun renderCharacters(characters: List<CharacterEntity>, offset:Int, text:String) {
        this.offset = offset
        this.mText = text
        for (character in characters)
            characterAdapter.addCharacter(character)
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save the user's current game state
        savedInstanceState.putInt("OFFSET", offset)
        savedInstanceState.putString("TEXT", mText)

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState)
    }

    override fun isLoading(): Boolean {
        return loading
    }

    override fun setLoadingIndicator(visible: Boolean) {
        loading = visible
    }

    override fun setError(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG)
    }

    override fun setPresenter(presenter: CharacterContract.Presenter) {
        characterPresenter = presenter
    }

    override fun onDestroy() {
        if(disposable?.isDisposed ?:false)
            disposable?.dispose()
        characterPresenter?.stop()
        super.onDestroy()
    }
}
