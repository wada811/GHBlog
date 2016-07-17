package com.wada811.rxviewmodel

import com.wada811.observablemodel.ObservableSynchronizedArrayList
import com.wada811.rxviewmodel.extensions.ToRxArrayList
import org.junit.Assert
import org.junit.Test
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SchedulerTest {
    @Throws(InterruptedException::class)
    fun assertConcurrent(message: String, maxTimeoutSeconds: Int, threadCount: Int, action: () -> Unit) {
        assertConcurrent(message, maxTimeoutSeconds, Collections.nCopies(threadCount, action))
    }

    @Throws(InterruptedException::class)
    fun assertConcurrent(message: String, maxTimeoutSeconds: Int, vararg actions: () -> Unit) {
        assertConcurrent(message, maxTimeoutSeconds, actions.asList())
    }

    @Throws(InterruptedException::class)
    fun assertConcurrent(message: String, maxTimeoutSeconds: Int, actions: List<() -> Unit>) {
        val numThreads = actions.size
        val exceptions = Collections.synchronizedList(ArrayList<Throwable>())
        val threadPool = Executors.newFixedThreadPool(numThreads)
        try {
            val allExecutorThreadsReady = CountDownLatch(numThreads)
            val afterInitBlocker = CountDownLatch(1)
            val allDone = CountDownLatch(numThreads)
            actions.forEach { action ->
                threadPool.submit {
                    allExecutorThreadsReady.countDown()
                    try {
                        afterInitBlocker.await()
                        action()
                    } catch (e: Throwable) {
                        exceptions.add(e)
                    } finally {
                        allDone.countDown()
                    }
                }
            }
            // wait until all threads are ready
            Assert.assertTrue("Timeout initializing threads! Perform long lasting initializations before passing actions to assertConcurrent", allExecutorThreadsReady.await((actions.size * 10).toLong(), TimeUnit.MILLISECONDS))
            // start all test runners
            afterInitBlocker.countDown()
            Assert.assertTrue("$message timeout! More than $maxTimeoutSeconds seconds", allDone.await(maxTimeoutSeconds.toLong(), TimeUnit.SECONDS))
        } finally {
            threadPool.shutdownNow()
        }
        Assert.assertTrue("$message failed with exception(s): " + exceptions, exceptions.isEmpty())
    }

    @Test
    fun worker() {
        Schedulers.immediate().createWorker().schedule { System.out.println("test") }
    }

    @Test
    fun RxArrayList() {
        UIThreadScheduler.DefaultScheduler = Schedulers.newThread()
        val list = ObservableSynchronizedArrayList<String>()
        val rxList = list.ToRxArrayList { it + it }
        assertConcurrent("""list.add("item")""", 1, 100, { list.add("item") })
        Assert.assertEquals(100, rxList.size)
        list.forEachIndexed { index, item -> System.out.println("list.forEachIndexed: index: $index, item: $item") }
        rxList.forEachIndexed { index, item -> System.out.println("rxList.forEachIndexed: index: $index, item: $item") }
    }
}