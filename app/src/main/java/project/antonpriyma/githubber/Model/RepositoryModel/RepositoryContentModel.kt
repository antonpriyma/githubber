

package com.antonpriyma.githubber.Model.RepositoryModel

data class RepositoryContentModel(
    val _links: Links,
    val download_url: String,
    val git_url: String,
    val html_url: String,
    val name: String,
    val path: String,
    val sha: String,
    val size: Int,
    val type: String,
    val url: String
)