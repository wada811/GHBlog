package com.wada811.ghblog.domain.model

import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate

class GitTree(
    path: String,
    mode: String,
    type: String,
    sha: String,
    content: String,
    baseTree: String
) : INotifyPropertyChanged {
    val path: String by PropertyChangedDelegate(path)
    val mode: String by PropertyChangedDelegate(mode)
    val type: String by PropertyChangedDelegate(type)
    val sha: String by PropertyChangedDelegate(sha)
    val content: String by PropertyChangedDelegate(content)
    val baseTree: String by PropertyChangedDelegate(baseTree)

}