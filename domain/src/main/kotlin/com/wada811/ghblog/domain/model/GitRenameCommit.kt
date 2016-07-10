package com.wada811.ghblog.domain.model

import com.wada811.ghblog.domain.util.Base64
import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate

class GitRenameCommit(oldPath: String, path: String, message: String, content: String, sha: String) : INotifyPropertyChanged {
    var oldPath: String by PropertyChangedDelegate(oldPath)
    var path: String by PropertyChangedDelegate(path)
    var message: String by PropertyChangedDelegate(message)
    var content: String by PropertyChangedDelegate(content)
    var sha: String by PropertyChangedDelegate(sha)
    fun encodedContent(): String = Base64.encodeToString(content.toByteArray(), Base64.NO_WRAP)
}