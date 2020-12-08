

package com.antonpriyma.githubber.Model.RepositoryModel

data class RepoDirModel (
    val name: String,
    val path: String,
    val content: ArrayList<RepositoryContentModel>
)