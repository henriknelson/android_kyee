package nu.cliffords.android_kyee.presenters

import javax.inject.Inject

/**
 * Created by Henrik Nelson on 2017-08-24.
 */

open class BasePresenter<T>() {

    var view: T? = null

    fun bind(view: T){
        this.view = view
    }

    fun unbind(){
        this.view = null
    }

}