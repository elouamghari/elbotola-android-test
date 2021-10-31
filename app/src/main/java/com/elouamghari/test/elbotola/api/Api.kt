package com.elouamghari.test.elbotola.api

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object Api {

    fun <E> call(call: Observable<E>, observer: Observer<E>){
        call.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(observer)
    }

}