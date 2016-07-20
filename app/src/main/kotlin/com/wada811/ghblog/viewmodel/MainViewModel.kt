package com.wada811.ghblog.viewmodel

import android.view.View
import com.wada811.ghblog.view.activity.MainActivity
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxMessenger
import com.wada811.rxviewmodel.RxViewModel

class MainViewModel : RxViewModel() {
    var next = RxCommand(View.OnClickListener {
        RxMessenger.send(MainActivity.NextAction())
    }).asManaged()
}