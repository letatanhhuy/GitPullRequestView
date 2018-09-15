package sample.huy.gitpullrequestview.UI.Fragment

import android.app.ProgressDialog
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import sample.huy.gitpullrequestview.UI.DiffSupporter
import sample.huy.gitpullrequestview.UI.RecycleAdapter.PrFileRecycleAdapter
import javax.inject.Inject
import android.text.method.ScrollingMovementMethod



class CompareFragment:Fragment() , PrFileRecycleAdapter.ItemClickListener {
    private var curPRIndex = -1

    //UI
    private lateinit var progressDialog: ProgressDialog
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
        curPRIndex = this.arguments?.get(ConfigurationValue.PR_INDEX) as Int
        Log.d(TAG, "onCreateView: pull request file index:" + curPRIndex)
        fetchDataPullRequestFile()
        return initUI(inflater?.inflate(R.layout.compare_pr_view, container, false))
    }

    override fun onStart() {
        super.onStart()
        activity?.title = "Compare PR " + curPRIndex
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        updateCompareView()
    }

    private fun fetchDataPullRequestFile() {
        if(curPRIndex == -1) {
            return
        }
        progressDialog = ProgressDialog(this.activity)
        progressDialog.show()
        DaggerRetrofitProviderInterface.create().getRetroFitProvider(this)
        val service: NetworkServices = retroFitObj.getRetrofitInstance().create(NetworkServices::class.java)
        val call = service.getAllPullRequestFiles(curPRIndex)
        call.enqueue(object : Callback<List<PullRequestFile>> {
            override fun onResponse(call: Call<List<PullRequestFile>>, response: Response<List<PullRequestFile>>) {
                dataArrayList.clear()
                for (file in response.body().orEmpty()) {
                    var prFile = DiffSupporter.processPullRequestFileDiff(file)
                    dataArrayList.add(prFile)
                }
                recycleViewPrFileListAdapter.notifyDataSetChanged()
                Log.d(TAG, "fetch data success size of PR file list:" + dataArrayList.size)
                progressDialog.dismiss()
                updateCompareView()
            }

            override fun onFailure(call: Call<List<PullRequestFile>>, t: Throwable) {
                Toast.makeText(activity, getString(R.string.loading_fail), Toast.LENGTH_SHORT).show()
                Log.e(TAG, "fetch data failed error:" + t.message)
                progressDialog.dismiss()
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
    private fun updateCompareView() {
        if (dataArrayList.size > 0) {
            updateCompareView(0)
        } else {

        }
    }
    private fun updateCompareView(fileIndex:Int) {
        if (isLandscapeMode()) {
            var originTextView = view?.findViewById<TextView>(R.id.txtContentOrigin)
            var newTextView = view?.findViewById<TextView>(R.id.txtContentNew)
            originTextView?.setHorizontallyScrolling(true)
            originTextView?.movementMethod = ScrollingMovementMethod()
            newTextView?.setHorizontallyScrolling(true)
            newTextView?.movementMethod = ScrollingMovementMethod()
            if(fileIndex >= 0) {
                originTextView?.text = DiffSupporter.fromHtml(dataArrayList.get(fileIndex).originContent!!)
                newTextView?.text = DiffSupporter.fromHtml(dataArrayList.get(fileIndex).newContent!!)
            } else {
                originTextView?.text = getString(R.string.empty_content)
                newTextView?.text = getString(R.string.empty_content)
            }
        } else {
            var combineTextView = view?.findViewById<TextView>(R.id.txtContentCombine)
            if(fileIndex >= 0) {
                combineTextView?.text = DiffSupporter.fromHtml(dataArrayList.get(fileIndex).differences!!)
            } else {
                combineTextView?.text = getString(R.string.empty_content)
            }
        }

    }

    private fun isLandscapeMode():Boolean {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    override fun onItemClick(view: View, fileIndex: Int) {
        Log.d(TAG, "onItemClick file index:" + fileIndex)
        updateCompareView(fileIndex)
    }


}