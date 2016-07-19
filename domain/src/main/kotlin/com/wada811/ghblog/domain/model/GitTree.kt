package com.wada811.ghblog.domain.model

import com.wada811.observablemodel.events.property.INotifyPropertyChanged
import com.wada811.observablemodel.events.property.PropertyChangedDelegate

class GitTree(
    nodes: List<Node>,
    baseTree: String
) : INotifyPropertyChanged {
    class Node(
        path: String,
        mode: String,
        type: String,
        sha: String,
        content: String
    ) : INotifyPropertyChanged {
        val path: String by PropertyChangedDelegate(path)
        val mode: String by PropertyChangedDelegate(mode)
        val type: String by PropertyChangedDelegate(type)
        val sha: String by PropertyChangedDelegate(sha)
        val content: String by PropertyChangedDelegate(content)
    }

    val nodes: List<Node> by PropertyChangedDelegate(nodes)
    val baseTree: String by PropertyChangedDelegate(baseTree)
}