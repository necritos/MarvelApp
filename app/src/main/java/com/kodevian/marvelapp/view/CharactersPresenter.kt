package com.kodevian.marvelapp.view



import com.kodevian.marvelapp.BuildConfig
import com.kodevian.marvelapp.data.request.MarvelRequest
import com.kodevian.marvelapp.data.request.ServiceGeneratorSimple
import com.kodevian.marvelapp.data.response.BaseResponse
import com.kodevian.marvelapp.model.CharacterEntity
import com.kodevian.marvelapp.utils.md5
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class CharactersPresenter(internal var mView: CharacterContract.View) : CharacterContract.Presenter {
    override fun stop() {
        if(subscriptions.size()>0)
            subscriptions.clear()
    }

    var subscriptions: CompositeDisposable = CompositeDisposable()

    override fun getCharacter(offset:Int, s:String) {

        if ( !mView.isLoading()) {
            mView.setLoadingIndicator(true)
            val request: MarvelRequest = ServiceGeneratorSimple.createServiceRx(MarvelRequest::class.java)
            val public_key:String = BuildConfig.PUBLIC_KEY
            val private_key:String = BuildConfig.PRIVATE_KEY
            val ts = (System.currentTimeMillis() / 1000).toString()
            val hash = md5("$ts$private_key$public_key")
            val options:MutableMap<String,String> = mutableMapOf()
            options.put("offset", offset.toString())
            options.put("orderBy", MarvelRequest.OrderBy.NAME.value)
            options.put("limit", 15.toString())
            if(!s.isEmpty())
                options.put("nameStartsWith", s)

            subscriptions.add(request.getCharacters(
                    public_key,
                    ts,
                    hash,
                    options
                    )
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { t: BaseResponse<CharacterEntity>? ->
                        if(t?.code==200){
                             t.data
                        }else{
                            null
                        }
                    }
                    .subscribe ({
                        d ->
                            if(d!=null){
                                val characters: List<CharacterEntity> = d.results
                                mView.renderCharacters(characters, d.offset + 15, s)
                            }else{
                                mView.setError("Sorry :( ")
                            }

                    },{
                        mView.setLoadingIndicator(false)
                        mView.setError("Fail")
                    },{
                        mView.setLoadingIndicator(false)
                    }))
        }
    }




    override fun start() {
        mView.setPresenter(this)
    }


}
