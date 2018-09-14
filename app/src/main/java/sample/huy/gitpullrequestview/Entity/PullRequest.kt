package sample.huy.gitpullrequestview.Entity

import com.google.gson.annotations.SerializedName

data class PullRequest(
        @field:SerializedName("title")
        var title: String?,
        @field:SerializedName("state")
        var state: String?,
        @field:SerializedName("body")
        var body: String?)