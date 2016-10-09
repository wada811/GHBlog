package com.wada811.ghblog.data

import com.wada811.logforest.LogForest
import com.wada811.logforest.LogLevel
import com.wada811.logforest.LogTree
import com.wada811.logforest.LogWood
import org.junit.After
import org.junit.Before
import org.junit.Test
import rx.Observable
import rx.observers.TestSubscriber


class ObservableTest {

    val logTree = object : LogTree() {
        override fun log(level: LogLevel, tag: String, message: String, t: Throwable?) {
            System.out.println("$tag: $message")
        }
    }

    @Before
    fun setUp() {
        LogForest.plant(logTree)
    }

    @After
    fun tearDown() {
        LogForest.fell(logTree)
    }

    @Test
    fun mergeDelayError() {
        LogWood.d("mergeDelayError")
        val testSubscriber = TestSubscriber<Int>()

        Observable.mergeDelayError(Observable.just(1, 2), Observable.error<Int>(null), Observable.just(4, 5))
            .doOnNext { LogWood.e("onNext: $it") }
            .doOnError { LogWood.e("onError: $it") }
            .doOnCompleted { LogWood.e("onCompleted") }
            .subscribe(testSubscriber)
        testSubscriber.assertValues(1, 2, 4, 5)
        testSubscriber.assertError(NullPointerException::class.java)
    }

    @Test
    fun onErrorResumeNext() {
        LogWood.d("onErrorResumeNext")
        val testSubscriber = TestSubscriber<Int>()

        val observable1 = Observable.merge(Observable.just(1, 2), Observable.error<Int>(null), Observable.just(4, 5))
        val observable2 = Observable.just(6, 7, 8, 9, 10)
        observable1.onErrorResumeNext { observable2 }
            .doOnNext { LogWood.e("onNext: $it") }
            .doOnError { LogWood.e("onError: $it") }
            .doOnCompleted { LogWood.e("onCompleted") }
            .subscribe(testSubscriber)
        testSubscriber.assertValues(1, 2, 6, 7, 8, 9, 10)
    }
}