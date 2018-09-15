package sample.huy.gitpullrequestview.Constant

class ConfigurationValue {
    companion object {
        //https://api.github.com/repos/letatanhhuy/prTracking/pulls
        //https://api.github.com/repos/letatanhhuy/prTracking/pulls/1/files
        //REst API - network
        const val BASE_URL = "https://api.github.com/"
        const val GET_PR_URL = "repos/letatanhhuy/prTracking/pulls"
        const val GET_PR_FILES_URL = "repos/letatanhhuy/prTracking/pulls/{id}/files"
        //Fragment argument key
        const val PR_INDEX = "PR_INDEX"
    }
}