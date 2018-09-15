package sample.huy.gitpullrequestview.UI.Fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import sample.huy.gitpullrequestview.Constant.ConfigurationValue
import sample.huy.gitpullrequestview.R
class CompareFragment:Fragment() {
    private var curIndex = -1
    companion object {
        private val TAG = "GPT-CompareFragment"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        curIndex = this.arguments?.get(ConfigurationValue.PR_INDEX) as Int
        Log.d(TAG, "onCreateView:" + curIndex)
        return inflater?.inflate(R.layout.compare_pr_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        (this.view?.findViewById(R.id.txtCompareId) as TextView).text = "Compare PR " + curIndex
    }
}