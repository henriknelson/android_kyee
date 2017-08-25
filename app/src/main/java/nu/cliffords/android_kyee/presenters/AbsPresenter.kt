package nu.cliffords.android_kyee.presenters

import javax.inject.Inject

/**
 * Created by henrik on 2017-08-24.
 */
open class AbsPresenter<T> @Inject constructor() {

    var view: T? = null

    fun bind(view: T){
        this.view = view
    }

    fun unbind(){
        this.view = null
    }
}