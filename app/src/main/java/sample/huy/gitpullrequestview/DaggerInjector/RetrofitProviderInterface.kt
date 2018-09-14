package sample.huy.gitpullrequestview.DaggerInjector

import dagger.Component
import sample.huy.gitpullrequestview.MainActivity
import sample.huy.gitpullrequestview.UI.Fragment.PrListFragment

@Component
interface RetrofitProviderInterface {
    fun getRetroFitProvider(app: MainActivity)
    fun getRetroFitProvider(app: PrListFragment)
}