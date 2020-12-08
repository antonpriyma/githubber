

package com.antonpriyma.githubber.Repository

import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Network.SafeApiRequest

class NetworkRepository(
    private val api: GithubApiInterface
) : SafeApiRequest() {

    suspend fun getNotification (token:String, page:Int) = apiRequest { api.getNotification(token, page) }

    suspend fun getFeeds (token:String, username: String, page:Int) = apiRequest { api.getEvents(token, username, page) }

    suspend fun getLoginProfile (token:String) = apiRequest { api.getUserInfo(token) }

    suspend fun getUserProfile (token:String, username: String) = apiRequest { api.getPublicUser(token, username) }

    suspend fun getMyTopRepositories (token:String) = apiRequest { api.myTopRepositories(token) }

    suspend fun getUserTopRepositories (token:String, username: String) = apiRequest { api.topReposUser(token, username) }

    suspend fun getRepoEvents (token:String, owner: String, repo: String, page: Int) = apiRequest { api.getRepoEvents(token, owner, repo, page) }

    suspend fun getMyRepositories (token:String, page: Int) = apiRequest { api.getMyRepositories(token, page) }

    suspend fun getOtherRepositories (token:String, username: String, page:Int) = apiRequest { api.getUserRepositories(token, username, page) }

    suspend fun getMyStarredRepositories (token:String, page: Int) = apiRequest { api.starredRepositoryLogin(token, page) }

    suspend fun getOtherStarredRepositories (token:String, username: String, page: Int) = apiRequest { api.starredRepositoryUser(token, username, page) }

    suspend fun getIssues (token:String, filter: String) = apiRequest { api.getIssues(token,"application/vnd.github.html,application/vnd.github.VERSION.raw" , filter, 80) }

    suspend fun getSearchUser(token:String, username: String) = apiRequest { api.searchUser(token, username) }

    suspend fun getSearchRepo(token:String, repo: String) = apiRequest { api.searchRepo(token, repo) }

    suspend fun getSearchUserSmall(token:String, username: String) = apiRequest { api.searchUserSmall(token, username) }

    suspend fun getSearchRepoSmall(token:String, repo: String) = apiRequest { api.searchRepoSmall(token, repo) }

    suspend fun getFollowers(token:String, username: String, page: Int) = apiRequest { api.getFollowers(token, username, page) }

    suspend fun getFollowing(token:String, username: String, page: Int) = apiRequest { api.getFollowing(token, username, page) }

    suspend fun getRepoDetails(token:String, username: String, repo: String) = apiRequest { api.getReposData(token, username, repo) }

    suspend fun getRepoContent(token:String, username: String, repo: String, path: String)
            = apiRequest { api.getReposContent(token, username, repo, path) }

    suspend fun getRepoReadme(token:String, username: String, repo: String) = apiRequest { api.getReposReadme(token, username, repo) }

    suspend fun getOrganizations(token:String, username: String) = apiRequest { api.getOrgs(token, username) }

    suspend fun getStar(token:String, owner: String, repo: String) = apiResponseCode { api.getStar(token, owner, repo) }

    suspend fun putStar(token:String, owner: String, repo: String) = apiResponseCode { api.starTheRepo(token, owner, repo) }

    suspend fun deleteStar(token:String, owner: String, repo: String) = apiResponseCode { api.removeStar(token, owner, repo) }

    suspend fun getFollow(token:String, owner: String) = apiResponseCode { api.checkFollow(token, owner) }

    suspend fun putFollow(token:String, owner: String) = apiResponseCode { api.followUser(token, owner) }

    suspend fun deleteFollow(token:String, owner: String) = apiResponseCode { api.unfollowUser(token, owner) }


}