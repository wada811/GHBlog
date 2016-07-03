package com.wada811.ghblog.model.domain

import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate
import org.apache.commons.codec.binary.Base64

class GitCommit(path: String, message: String, content: String, sha: String? = null) : INotifyPropertyChanged {
    var path: String by PropertyChangedDelegate(path)
    var message: String by PropertyChangedDelegate(message)
    var content: String by PropertyChangedDelegate(content)
    var sha: String? by PropertyChangedDelegate(sha)
    var encodedContent: String by PropertyChangedDelegate(Base64.encodeBase64String(content.toByteArray()))
}