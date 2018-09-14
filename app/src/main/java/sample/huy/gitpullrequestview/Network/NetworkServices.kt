package sample.huy.gitpullrequestview.Network

import retrofit2.Call
import retrofit2.http.GET
import sample.huy.gitpullrequestview.Constant.ConfigurationValue
import sample.huy.gitpullrequestview.Entity.PullRequest

interface NetworkServices {
    @GET(ConfigurationValue.GET_PR_URL)
    fun getAllPullRequests(): Call<List<PullRequest>>
}