package com.wada811.ghblog

import com.wada811.ghblog.domain.model.GitCommit
import com.wada811.notifypropertychanged.PropertyChangedEventArgs
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.extensions.ObserveProperty
import com.wada811.rxviewmodel.extensions.PropertyChangedAsObservable
import com.wada811.rxviewmodel.extensions.toRxProperty
import org.junit.Assert
import org.junit.Test
import java.util.*

class NotifyTest {
    @Test
    fun notifyTest() {
        val commit = GitCommit("path", "message", "content")
        val sub = { sender: Any, e: PropertyChangedEventArgs ->
            System.out.println("PropertyChanged: e.PropertyName: " + e.PropertyName)
            System.out.println("PropertyChanged: commit.path: " + (sender as GitCommit).path)
            Assert.assertEquals("path2", sender.path)
        }
        commit.PropertyChanged += sub
        commit.path = "path2"
        commit.PropertyChanged -= sub
        commit.path = "path3"
        val subscribe = commit.PropertyChangedAsObservable().subscribe {
            System.out.println("PropertyChangedAsObservable: it.PropertyName: " + it.PropertyName)
            System.out.println("PropertyChangedAsObservable: commit.path: " + commit.path)
            Assert.assertEquals("path4", commit.path)
        }
        commit.path = "path4"
        subscribe.unsubscribe()
        commit.path = "path5"
        val subscribe2 = commit.ObserveProperty("path", { it.path }).subscribe { it ->
            System.out.println("ObserveProperty: it: " + it)
            System.out.println("ObserveProperty: commit.path: " + commit.path)
            Assert.assertEquals("path6", it)
        }
        commit.path = "path6"
        subscribe2.unsubscribe()
        commit.path = "path7"
        val rxPath = commit.ObserveProperty("path", { it.path }).toRxProperty(commit.path, EnumSet.of(RxProperty.Mode.DISTINCT_UNTIL_CHANGED))
        val subscribe3 = rxPath.asObservable().subscribe {
            System.out.println("RxProperty: it: " + it)
            System.out.println("RxProperty: commit.path: " + commit.path)
            Assert.assertEquals("path8", it)
        }
        commit.path = "path8"
        subscribe3.unsubscribe()
        commit.path = "path9"
        val rxPath2 = commit.ObserveProperty("path", { it.path }).map { it.toUpperCase() }.toRxProperty(commit.path.toUpperCase(), EnumSet.of(RxProperty.Mode.DISTINCT_UNTIL_CHANGED))
        val subscribe4 = rxPath2.asObservable().subscribe {
            System.out.println("RxProperty: it: " + it)
            System.out.println("RxProperty: commit.path: " + commit.path)
            Assert.assertEquals("PATH10", it)
        }
        commit.path = "path10"
        subscribe4.unsubscribe()
        commit.path = "path11"
    }
}