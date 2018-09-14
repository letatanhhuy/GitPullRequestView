package sample.huy.gitpullrequestview.DaggerInjector

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sample.huy.gitpullrequestview.Constant.ConfigurationValue
import javax.inject.Inject

class RetroFitProvider  @Inject constructor() {
    fun getRetrofitInstance(): Retrofit {
        return retrofit2.Retrofit.Builder()
                .baseUrl(ConfigurationValue.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}