package sample.huy.gitpullrequestview.DaggerInjector

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import sample.huy.gitpullrequestview.Constant.ConfigurationValue
import javax.inject.Inject
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


class RetroFitProvider  @Inject constructor() {
    fun getRetrofitInstance(): Retrofit {
        return retrofit2.Retrofit.Builder()
                .client(getRequestHeader())
                .baseUrl(ConfigurationValue.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun getRequestHeader(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
    }
}