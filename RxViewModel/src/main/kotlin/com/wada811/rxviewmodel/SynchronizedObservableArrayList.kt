package com.wada811.rxviewmodel

import android.databinding.ObservableArrayList
import java.util.*

class SynchronizedObservableArrayList<T>(var list: ObservableArrayList<T>) : MutableList<T> by Collections.synchronizedList(list) {
}
