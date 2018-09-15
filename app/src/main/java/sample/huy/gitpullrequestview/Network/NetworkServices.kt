package sample.huy.gitpullrequestview.Network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import sample.huy.gitpullrequestview.Constant.ConfigurationValue
import sample.huy.gitpullrequestview.Entity.PullRequest
import sample.huy.gitpullrequestview.Entity.PullRequestFile

interface NetworkServices {
    @GET(ConfigurationValue.GET_PR_URL)
    fun getAllPullRequests(): Call<List<PullRequest>>

    @GET(ConfigurationValue.GET_PR_FILES_URL)
    fun getAllPullRequestFiles(@Path("id")groupId:Int): Call<List<PullRequestFile>>
}