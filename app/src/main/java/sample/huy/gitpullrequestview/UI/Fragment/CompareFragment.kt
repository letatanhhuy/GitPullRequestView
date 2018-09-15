package sample.huy.gitpullrequestview.UI.Fragment

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
import sample.huy.gitpullrequestview.Entity.PullRequestFile
import sample.huy.gitpullrequestview.Network.NetworkServices
import sample.huy.gitpullrequestview.R
import sample.huy.gitpullrequestview.UI.RecycleAdapter.PrFileRecycleAdapter
import javax.inject.Inject

class CompareFragment:Fragment() , PrFileRecycleAdapter.ItemClickListener {
    private var curFileIndex = -1

    //UI
//    private lateinit var progressDoalog: ProgressDialog
    private lateinit var recycleViewPrFileList: RecyclerView
    private lateinit var recycleViewPrFileListAdapter: PrFileRecycleAdapter

    //Data
    private var dataArrayList = ArrayList<PullRequestFile>()

    //Dagger
    @Inject
    lateinit var retroFitObj: RetroFitProvider

    companion object {
        private val TAG = "GPT-CompareFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        curFileIndex = this.arguments?.get(ConfigurationValue.PR_INDEX) as Int
        Log.d(TAG, "onCreateView: pull request file index:" + curFileIndex)
        fetchDataPullRequestFile()
        return initUI(inflater?.inflate(R.layout.list_pr_file_view, container, false))
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "Compare PR " + curFileIndex
        fetchDataPullRequestFile()
    }

    private fun fetchDataPullRequestFile() {
        if(curFileIndex == -1) {
            return
        }
        DaggerRetrofitProviderInterface.create().getRetroFitProvider(this)
        val service: NetworkServices = retroFitObj.getRetrofitInstance().create(NetworkServices::class.java)
        val call = service.getAllPullRequestFiles(curFileIndex)
        call.enqueue(object : Callback<List<PullRequestFile>> {
            override fun onResponse(call: Call<List<PullRequestFile>>, response: Response<List<PullRequestFile>>) {
                dataArrayList.clear()
                //progressDoalog.dismiss()
                for (file in response.body().orEmpty()) {
                    dataArrayList.add(file)
                }
                recycleViewPrFileListAdapter.notifyDataSetChanged()
                Log.d(TAG, "fetch data success size of PR file list:" + dataArrayList.size)
            }

            override fun onFailure(call: Call<List<PullRequestFile>>, t: Throwable) {
                //progressDoalog.dismiss()
                Toast.makeText(activity, getString(R.string.loading_fail), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "fetch data failed error:" + t.message)
            }
        })
    }

    private fun initUI(view: View) : View{
        recycleViewPrFileList = view.findViewById(R.id.recycleViewPRFile) as RecyclerView
        recycleViewPrFileListAdapter = PrFileRecycleAdapter(activity, dataArrayList)
        recycleViewPrFileListAdapter.setClickListener(this)
        recycleViewPrFileList.adapter = recycleViewPrFileListAdapter
        recycleViewPrFileList.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onItemClick(view: View, position: Int) {
        //implement later
    }
}