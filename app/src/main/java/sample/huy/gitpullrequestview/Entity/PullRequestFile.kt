package sample.huy.gitpullrequestview.Entity

import com.google.gson.annotations.SerializedName

data class PullRequestFile(
        @field:SerializedName("filename")
        var name: String?,
        @field:SerializedName("status")
        var status: String?,
        @field:SerializedName("patch")
        var differences: String?)