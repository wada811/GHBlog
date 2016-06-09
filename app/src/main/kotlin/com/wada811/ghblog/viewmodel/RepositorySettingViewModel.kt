package com.wada811.ghblog.viewmodel

import android.databinding.ObservableArrayList
import android.util.Log
import android.view.View
import com.wada811.rxviewmodel.RxCommand
import com.wada811.rxviewmodel.RxProperty
import com.wada811.rxviewmodel.RxViewModel
import com.wada811.rxviewmodel.extensions.toRxCommand

class RepositorySettingViewModel : RxViewModel() {
    var repositoryNameList: ObservableArrayList<String> = ObservableArrayList()
    var selectedRepositoryName = RxProperty<String?>().asManaged()
    var select = RxCommand(View.OnClickListener {}).asManaged()
    var save = selectedRepositoryName
                    .asObservable()
                    .map { it != null }
                    .toRxCommand(View.OnClickListener {
                        Log.e("wada", "save button clicked!")
                        Log.e("wada", "selectedRepositoryName: " + selectedRepositoryName.value)
                    })
                    .asManaged()
}