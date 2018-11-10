package sg.com.sph.testdrivenmvvm

import io.reactivex.Observable

interface DataRepository {
    fun fetchData(): Observable<String>
}