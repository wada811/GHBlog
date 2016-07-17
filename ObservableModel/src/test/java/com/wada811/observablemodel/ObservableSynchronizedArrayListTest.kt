package com.wada811.observablemodel

import com.wada811.observablemodel.extensions.CollectionChangedAsObservable
import com.wada811.observablemodel.extensions.ToObservableSynchronizedArrayList
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class ObservableSynchronizedArrayListTest {

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
    fun CollectionChanged() {
        val threadCount = 100
        val list = ObservableSynchronizedArrayList<String>()
        val sub = { sender: Any, e: CollectionChangedEventArgs ->
            when (e.action) {
                CollectionChangedEventAction.Add -> {
                    val eventArgs = e as CollectionChangedEventArgs.Add
                    System.out.println("CollectionChanged: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("CollectionChanged: eventArgs.index: ${eventArgs.index}")
                    System.out.println("CollectionChanged: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Remove -> {
                    val eventArgs = e as CollectionChangedEventArgs.Remove
                    System.out.println("CollectionChanged: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("CollectionChanged: eventArgs.index: ${eventArgs.index}")
                    System.out.println("CollectionChanged: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Replace -> {
                    val eventArgs = e as CollectionChangedEventArgs.Replace
                    System.out.println("CollectionChanged: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("CollectionChanged: eventArgs.index: ${eventArgs.index}")
                    System.out.println("CollectionChanged: eventArgs.oldItem: ${eventArgs.oldItem}")
                    System.out.println("CollectionChanged: eventArgs.newItem: ${eventArgs.newItem}")
                }
                CollectionChangedEventAction.Move -> {
                    val eventArgs = e as CollectionChangedEventArgs.Move
                    System.out.println("CollectionChanged: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("CollectionChanged: eventArgs.oldIndex: ${eventArgs.oldIndex}")
                    System.out.println("CollectionChanged: eventArgs.newIndex: ${eventArgs.newIndex}")
                    System.out.println("CollectionChanged: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Reset -> {
                    val eventArgs = e as CollectionChangedEventArgs.Reset
                    System.out.println("CollectionChanged: eventArgs: ${eventArgs.javaClass.name}")
                }
            }
        }
        list.CollectionChanged += sub
        assertConcurrent("""list.add("item")""", 1, threadCount, { list.add("item") })
        assertConcurrent("""list.forEachIndexed""", 1, {
            list.readLockAction {
                list.forEachIndexed { index, item ->
                    System.out.println("list.forEachIndexed: index: $index, item: $item")
                }
            }
        })
        assertConcurrent("""list.remove("item")""", 1, threadCount, { list.remove("item") })
        assertEquals(0, list.size)
        assertConcurrent("""list.add("item") list.remove("item")""", 1, threadCount, {
            list.add("item")
            list.remove("item")
        })
        assertEquals(0, list.size)
        assertConcurrent("""list.add("item")""", 1, threadCount, { list.add("item") })
        assertConcurrent("""list.forEachIndexed""", 1, {
            list.readLockAction {
                list.forEachIndexed { index, item ->
                    System.out.println("list.forEachIndexed: index: $index, item: $item")
                }
            }
        })
        assertConcurrent("""list.clear()""", 1, threadCount, { list.clear() })
        assertEquals(0, list.size)
        assertConcurrent("""list.add("item") list.clear()""", 1, threadCount, {
            list.add("item")
            list.clear()
        })
        assertEquals(0, list.size)
        assertConcurrent("""list.addAll(listOf("item1", "item2", "item3"))""", 1, threadCount, { list.addAll(listOf("item1", "item2", "item3")) })
        assertConcurrent("""list.removeAll(listOf("item1", "item2"))""", 1, threadCount, { list.removeAll(listOf("item1", "item2")) })
        assertConcurrent("""list.removeAt(0)""", 1, threadCount, { list.removeAt(0) })
        assertEquals(0, list.size)
        assertConcurrent("""list.addAll(listOf("item1", "item2", "item3"))""", 1, threadCount, { list.addAll(listOf("item1", "item2", "item3")) })
        assertConcurrent("""list.removeAll(listOf("item1", "item2"))""", 1, threadCount, { list.removeAll(listOf("item1", "item2")) })
        assertConcurrent("""list.retainAll(listOf())""", 1, threadCount, { list.retainAll(listOf()) })
        assertEquals(0, list.size)
        assertConcurrent("""list.addAll(listOf("item1", "item2", "item3"))""", 1, threadCount, { list.addAll(listOf("item1", "item2", "item3")) })
        assertConcurrent("list.move(298, 299)", 1, { list.move(298, 299) })
        assertConcurrent("list.forEachIndexed", 1, {
            list.readLockAction {
                list.forEachIndexed { index, item ->
                    System.out.println("list.forEachIndexed: index: $index, item: $item")
                }
            }
        })
        assertEquals(threadCount * 3, list.size)
        assertConcurrent("list.clear()", 1, { list.clear() })
        assertConcurrent("""list.addAll(listOf("item1", "item2", "item3"))""", 1, threadCount, { list.addAll(listOf("item1", "item2", "item3")) })
        assertConcurrent("""list[299] = "item299"""", 1, { list[299] = "item299" })
        assertConcurrent("list.forEachIndexed", 1, {
            list.readLockAction {
                list.forEachIndexed { index, item ->
                    System.out.println("list.forEachIndexed: index: $index, item: $item")
                }
            }
        })
        assertEquals(threadCount * 3, list.size)
        assertConcurrent("list.clear()", 1, { list.clear() })
        assertEquals(0, list.size)
        list.CollectionChanged -= sub
    }

    @Test
    fun ToObservableSynchronizedArrayList() {
        val threadCount = 100
        val original = ObservableSynchronizedArrayList<String>()
        val converted = original.ToObservableSynchronizedArrayList { it + it }
        original.CollectionChangedAsObservable().subscribe({ e ->
            when (e.action) {
                CollectionChangedEventAction.Add -> {
                    val eventArgs = e as CollectionChangedEventArgs.Add
                    System.out.println("original: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("original: eventArgs.index: ${eventArgs.index}")
                    System.out.println("original: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Remove -> {
                    val eventArgs = e as CollectionChangedEventArgs.Remove
                    System.out.println("original: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("original: eventArgs.index: ${eventArgs.index}")
                    System.out.println("original: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Replace -> {
                    val eventArgs = e as CollectionChangedEventArgs.Replace
                    System.out.println("original: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("original: eventArgs.index: ${eventArgs.index}")
                    System.out.println("original: eventArgs.oldItem: ${eventArgs.oldItem}")
                    System.out.println("original: eventArgs.newItem: ${eventArgs.newItem}")
                }
                CollectionChangedEventAction.Move -> {
                    val eventArgs = e as CollectionChangedEventArgs.Move
                    System.out.println("original: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("original: eventArgs.oldIndex: ${eventArgs.oldIndex}")
                    System.out.println("original: eventArgs.newIndex: ${eventArgs.newIndex}")
                    System.out.println("original: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Reset -> {
                    val eventArgs = e as CollectionChangedEventArgs.Reset
                    System.out.println("original: eventArgs: ${eventArgs.javaClass.name}")
                }
            }
        }, {
            System.out.println("original: onError: $it")
        })
        converted.CollectionChangedAsObservable().subscribe({ e ->
            when (e.action) {
                CollectionChangedEventAction.Add -> {
                    val eventArgs = e as CollectionChangedEventArgs.Add
                    System.out.println("converted: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("converted: eventArgs.index: ${eventArgs.index}")
                    System.out.println("converted: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Remove -> {
                    val eventArgs = e as CollectionChangedEventArgs.Remove
                    System.out.println("converted: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("converted: eventArgs.index: ${eventArgs.index}")
                    System.out.println("converted: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Replace -> {
                    val eventArgs = e as CollectionChangedEventArgs.Replace
                    System.out.println("converted: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("converted: eventArgs.index: ${eventArgs.index}")
                    System.out.println("converted: eventArgs.oldItem: ${eventArgs.oldItem}")
                    System.out.println("converted: eventArgs.newItem: ${eventArgs.newItem}")
                }
                CollectionChangedEventAction.Move -> {
                    val eventArgs = e as CollectionChangedEventArgs.Move
                    System.out.println("converted: eventArgs: ${eventArgs.javaClass.name}")
                    System.out.println("converted: eventArgs.oldIndex: ${eventArgs.oldIndex}")
                    System.out.println("converted: eventArgs.newIndex: ${eventArgs.newIndex}")
                    System.out.println("converted: eventArgs.item: ${eventArgs.item}")
                }
                CollectionChangedEventAction.Reset -> {
                    val eventArgs = e as CollectionChangedEventArgs.Reset
                    System.out.println("converted: eventArgs: ${eventArgs.javaClass.name}")
                }
            }
        }, {
            System.out.println("converted: onError: $it")
        })
        assertConcurrent("""original.add("item")""", 1, threadCount, { original.add("item") })
        assertEquals(threadCount, original.size)
        assertEquals(threadCount, converted.size)
        assertConcurrent("""converted.add("item")""", 1, threadCount, { converted.add("item") })
        assertEquals(threadCount, original.size)
        assertEquals(threadCount * 2, converted.size)
        converted.unsubscribe()
        assertConcurrent("""original.add("item")""", 1, threadCount, { original.add("item") })
        assertEquals(threadCount * 2, original.size)
        assertEquals(threadCount * 2, converted.size)
    }
}