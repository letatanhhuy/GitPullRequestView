package sample.huy.gitpullrequestview.UI.RecycleAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import sample.huy.gitpullrequestview.Entity.PullRequest
import sample.huy.gitpullrequestview.R

open class PrRecycleAdapter
internal constructor(context: Context?, private val mData: List<PullRequest>)
    : RecyclerView.Adapter<PrRecycleAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_pr_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPrName?.text = mData.get(position).title
        holder.txtPrBody?.text = mData.get(position).body
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var txtPrName: TextView? = itemView.findViewById(R.id.txtPrName)
        internal var txtPrBody: TextView? = itemView.findViewById(R.id.txtPrBody)
        private var layoutView: View? = itemView.findViewById(R.id.layoutContactItem)

        init {
            layoutView?.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) {
                mClickListener!!.onItemClick(view, mData.get(position).id!!.toInt())
            }
        }
    }

    internal fun getItem(id: Int): PullRequest {
        return mData.get(id)
    }


    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, id: Int)
    }
}