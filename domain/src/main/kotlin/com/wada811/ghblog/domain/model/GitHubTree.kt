package com.wada811.ghblog.domain.model

import com.wada811.notifypropertychanged.INotifyPropertyChanged
import com.wada811.notifypropertychanged.PropertyChangedDelegate

class GitHubTree(
        sha: String,
        url: String,
        tree: List<Node>? = null,
        truncated: Boolean? = null
) : INotifyPropertyChanged {
    var sha: String by PropertyChangedDelegate(sha)
    var url: String by PropertyChangedDelegate(url)
    var tree: List<Node>? by PropertyChangedDelegate(tree)
    var truncated: Boolean? by PropertyChangedDelegate(truncated)

    class Node(
            path: String,
            mode: String,
            type: String,
            size: Int,
            sha: String,
            url: String
    ) : INotifyPropertyChanged {
        var path: String  by PropertyChangedDelegate(path)
        var mode: String  by PropertyChangedDelegate(mode)
        var type: String  by PropertyChangedDelegate(type)
        var size: Int  by PropertyChangedDelegate(size)
        var sha: String  by PropertyChangedDelegate(sha)
        var url: String by PropertyChangedDelegate(url)
    }
}