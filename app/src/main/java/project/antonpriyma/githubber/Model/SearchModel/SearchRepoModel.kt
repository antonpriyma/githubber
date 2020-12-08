

package com.antonpriyma.githubber.Model.SearchModel

data class SearchRepoModel(
    val incomplete_results: Boolean,
    val items: ArrayList<Item>,
    val total_count: Int
)