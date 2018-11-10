package sg.com.sph.testdrivenmvvm

import android.arch.lifecycle.MutableLiveData

class MyViewModel(private val dataRepository: DataRepository) {

    val testLiveData = MutableLiveData<String>()

    fun getStuff(){
    }
}