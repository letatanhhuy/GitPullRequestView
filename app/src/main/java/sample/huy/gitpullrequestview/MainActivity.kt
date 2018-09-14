package sample.huy.gitpullrequestview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sample.huy.gitpullrequestview.DaggerInjector.DaggerRetrofitProviderInterface
import sample.huy.gitpullrequestview.DaggerInjector.RetroFitProvider
import sample.huy.gitpullrequestview.Entity.PullRequest
import sample.huy.gitpullrequestview.Network.NetworkServices
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var retroFitObj: RetroFitProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchDataPullRequest()
    }

    fun fetchDataPullRequest() {
        DaggerRetrofitProviderInterface.create().getRetroFitProvider(this)
        val service:NetworkServices = retroFitObj.getRetrofitInstance().create(NetworkServices::class.java)
        val call = service.getAllPullRequests()
        call.enqueue(object : Callback<List<PullRequest>> {
            override fun onResponse(call: Call<List<PullRequest>>, response: Response<List<PullRequest>>) {
                Log.d("pikachu:", "outside")
                for (pr in response.body().orEmpty()){
                    Log.d("pikachu:", pr.title)
                }

            }
            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {

                Toast.makeText(this@MainActivity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
