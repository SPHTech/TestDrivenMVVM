package sg.com.sph.testdrivenmvvm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MyViewModelTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Mock
    lateinit var mockDataRepository: DataRepository

    @Mock
    lateinit var mockLiveDataObserver: Observer<String>

    lateinit var myViewModel: MyViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        myViewModel = MyViewModel(mockDataRepository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Given DataRepository returns data, when getStuff() called, then update live data`() {
        //Setting how up the mock behaves
        whenever(mockDataRepository.fetchData()).thenReturn(Observable.just("Data"))

        //Fire the test method
        myViewModel.getStuff()

        //Check that our live data is updated
        Assert.assertEquals("Data", myViewModel.testLiveData.value)
    }

    @Test
    fun `Given DataRepository returns error, when getStuff() called, then do not change live data`() {
        //Setting how up the mock behaves
        whenever(mockDataRepository.fetchData()).thenReturn(Observable.error(Throwable()))

        myViewModel.testLiveData.observeForever(mockLiveDataObserver)

        //Fire the test method
        myViewModel.getStuff()

        verify(mockLiveDataObserver, times(0)).onChanged(any())
    }
}