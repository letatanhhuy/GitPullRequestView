package sample.huy.gitpullrequestview.DaggerInjector

import dagger.Component
import sample.huy.gitpullrequestview.MainActivity

@Component
interface RetrofitProviderInterface {
    fun getRetroFitProvider(app: MainActivity)
}