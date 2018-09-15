package sample.huy.gitpullrequestview.DaggerInjector

import android.support.v4.app.Fragment
import dagger.Component
import sample.huy.gitpullrequestview.MainActivity
import sample.huy.gitpullrequestview.UI.Fragment.CompareFragment
import sample.huy.gitpullrequestview.UI.Fragment.PrListFragment

@Component
interface RetrofitProviderInterface {
    fun getRetroFitProvider(app: CompareFragment)
    fun getRetroFitProvider(app: PrListFragment)
}