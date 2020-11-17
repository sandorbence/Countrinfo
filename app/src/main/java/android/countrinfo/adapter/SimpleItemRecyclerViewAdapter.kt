package android.countrinfo.adapter

import android.countrinfo.R
import android.countrinfo.model.Info
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_info.view.*

class SimpleItemRecyclerViewAdapter :
    RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

    private val infoList = mutableListOf<Info>()

    var itemClickListener: InfoItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = infoList[position]

        holder.info = info

        holder.tvTitle.text = info.title
    }

    fun addAll(infos: List<Info>) {
        infoList.clear()
        val size = infoList.size
        infoList += infos
        notifyDataSetChanged()
    }

    override fun getItemCount() = infoList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView = itemView.tvTitle
        var info: Info? = null

        init {
            itemView.setOnClickListener {
                info?.let { info -> itemClickListener?.onItemClick(info) }
            }

            itemView.setOnLongClickListener { view ->
                info?.let { info ->
                    itemClickListener?.onItemLongClick(
                        adapterPosition,
                        view,
                        info
                    )
                }
                true
            }
        }
    }

    interface InfoItemClickListener {
        fun onItemClick(info: Info)
        fun onItemLongClick(position: Int, view: View, info: Info): Boolean
    }

}