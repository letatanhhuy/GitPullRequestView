package sample.huy.gitpullrequestview.UI.Fragment

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sample.huy.gitpullrequestview.Constant.ConfigurationValue
import sample.huy.gitpullrequestview.DaggerInjector.DaggerRetrofitProviderInterface
import sample.huy.gitpullrequestview.DaggerInjector.RetroFitProvider
import sample.huy.gitpullrequestview.Entity.PullRequest
import sample.huy.gitpullrequestview.Network.NetworkServices
import sample.huy.gitpullrequestview.R
import sample.huy.gitpullrequestview.UI.RecycleAdapter.PrRecycleAdapter
import javax.inject.Inject

class PrListFragment : Fragment() , PrRecycleAdapter.ItemClickListener {
    //UI
    private lateinit var progressDialog:ProgressDialog
    private lateinit var recycleViewPrList: RecyclerView
    private lateinit var recycleViewPrListAdapter: PrRecycleAdapter

    //Data
    private var dataArrayList = ArrayList<PullRequest>()

    //Dagger
    @Inject
    lateinit var retroFitObj: RetroFitProvider

    companion object {
        private val TAG = "GPT-PrListFragment"
        fun newInstance(): PrListFragment {
            return PrListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView")
        return initUI(inflater?.inflate(R.layout.list_pr_view, container, false))
    }

    override fun onAttach(context: Context?) {
        Log.d(TAG, "onAttach")
        super.onAttach(context)
        fetchDataPullRequest()
    }

    override fun onResume() {
        Log.d(TAG, "onResume")
        super.onResume()
    }

    private fun fetchDataPullRequest() {
        progressDialog = ProgressDialog(this.activity)
        progressDialog.show()
        DaggerRetrofitProviderInterface.create().getRetroFitProvider(this)
        val service: NetworkServices = retroFitObj.getRetrofitInstance().create(NetworkServices::class.java)
        val call = service.getAllPullRequests()
        call.enqueue(object : Callback<List<PullRequest>> {
            override fun onResponse(call: Call<List<PullRequest>>, response: Response<List<PullRequest>>) {
                dataArrayList.clear()
                progressDialog.dismiss()
                for (pr in response.body().orEmpty()) {
                    dataArrayList.add(pr)
                }
                Log.d(TAG, "fetch data success size of PR list:" + dataArrayList.size)
                recycleViewPrListAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<PullRequest>>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(activity, getString(R.string.loading_fail), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "fetch data failed error:" + t.message)
            }
        })
    }

    private fun initUI(view: View) : View{
        recycleViewPrList = view.findViewById(R.id.recycleViewPR) as RecyclerView
        recycleViewPrListAdapter = PrRecycleAdapter(activity, dataArrayList)
        recycleViewPrListAdapter.setClickListener(this)
        recycleViewPrList.adapter = recycleViewPrListAdapter
        recycleViewPrList.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onItemClick(view: View, id: Int) {
        val compareFragment = CompareFragment()

        val arguments = Bundle()
        arguments.putInt(ConfigurationValue.PR_INDEX, id)
        compareFragment.arguments = arguments
        activity?.supportFragmentManager!!
                .beginTransaction()
                .replace(R.id.main_layout, compareFragment, "Pull Request Diff")
                .addToBackStack(null)
                .commit()
    }
}