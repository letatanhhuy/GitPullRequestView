package sample.huy.gitpullrequestview.UI.RecycleAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import sample.huy.gitpullrequestview.Entity.PullRequestFile
import sample.huy.gitpullrequestview.R

open class PrFileRecycleAdapter
internal constructor(context: Context?, private val mData: List<PullRequestFile>)
    : RecyclerView.Adapter<PrFileRecycleAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_pr_file_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtPrFileName?.text = mData.get(position).name
        holder.txtPrFileStatus?.text = mData.get(position).status
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var txtPrFileName: TextView? = itemView.findViewById(R.id.txtPrFileName)
        internal var txtPrFileStatus: TextView? = itemView.findViewById(R.id.txtPrFileStatus)
        private var layoutView: View? = itemView.findViewById(R.id.layoutPrFileItem)

        init {
            layoutView?.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) {
                mClickListener!!.onItemClick(view, adapterPosition)
            }
        }
    }

    internal fun getItem(id: Int): PullRequestFile {
        return mData.get(id)
    }


    internal fun setClickListener(itemClickListener: ItemClickListener) {
        this.mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View, position: Int)
    }
}