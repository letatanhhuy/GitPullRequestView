package sample.huy.gitpullrequestview.UI.Fragment

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sample.huy.gitpullrequestview.DaggerInjector.DaggerRetrofitProviderInterface
import sample.huy.gitpullrequestview.DaggerInjector.RetroFitProvider
import sample.huy.gitpullrequestview.Entity.PullRequest
import sample.huy.gitpullrequestview.Network.NetworkServices
import sample.huy.gitpullrequestview.R
import sample.huy.gitpullrequestview.UI.PrRecycleAdapter
import javax.inject.Inject

class PrListFragment : Fragment() , PrRecycleAdapter.ItemClickListener {
    //UI
    private lateinit var progressDoalog: ProgressDialog
    private lateinit var recycleViewPrList: RecyclerView
    private lateinit var recycleViewPrListAdapter: PrRecycleAdapter

    //Data
    private var dataArrayList = ArrayList<PullRequest>()

    //Dagger
    @Inject
    lateinit var retroFitObj: RetroFitProvider

    companion object {
        fun newInstance(): PrListFragment {
            return PrListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        createLoadingScreen()
        return initUI(inflater?.inflate(R.layout.list_pr_view, container, false))
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        fetchDataPullRequest()
    }

    private fun fetchDataPullRequest() {
        DaggerRetrofitProviderInterface.create().getRetroFitProvider(this)
        val service: NetworkServices = retroFitObj.getRetrofitInstance().create(NetworkServices::class.java)
        val call = service.getAllPullRequests()
        call.enqueue(object : Callback<List<PullRequest>> {
            override fun onResponse(call: Call<List<PullRequest>>, response: Response<List<PullRequest>>) {
                dataArrayList.clear()
                progressDoalog.dismiss()
                for (pr in response.body().orEmpty()) {
                    dataArrayList.add(pr)
                }
                recycleViewPrListAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {
                progressDoalog.dismiss()
                Toast.makeText(activity, getString(R.string.loading_fail), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun createLoadingScreen() {
        progressDoalog = ProgressDialog(this.activity)
        progressDoalog.setMessage(getString(R.string.loading_text))
        progressDoalog.show()

    }

    private fun initUI(view: View) : View{
        recycleViewPrList = view.findViewById(R.id.recycleViewPR) as RecyclerView
//        recycleViewPrList.addItemDecoration(DividerItemDecoration(recycleViewPrList.getContext(), DividerItemDecoration.VERTICAL))
        recycleViewPrListAdapter = PrRecycleAdapter(activity, dataArrayList)
        recycleViewPrListAdapter.setClickListener(this)
        recycleViewPrList.adapter = recycleViewPrListAdapter
        recycleViewPrList.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(activity, "Click on " + recycleViewPrListAdapter.getItem(position).title, Toast.LENGTH_SHORT).show()
    }
}