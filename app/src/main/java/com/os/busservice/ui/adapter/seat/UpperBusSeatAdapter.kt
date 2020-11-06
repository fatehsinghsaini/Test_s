package com.os.busservice.ui.adapter.seat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.os.busservice.R
import com.os.busservice.listeners.OnSeatSelected
import com.os.busservice.model.seat.AbstractItem
import com.os.busservice.model.seat.CenterItem
import com.os.busservice.model.seat.EdgeItem

class UpperBusSeatAdapter(
    private val mContext: Context,
    items: List<AbstractItem>,
    private val mOnSeatSelected: OnSeatSelected
) : SelectableAdapter<RecyclerView.ViewHolder?>() {
    private class EdgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgSeat: ImageView
        val imgSeatSelected: ImageView

        init {
            imgSeat = itemView.findViewById<View>(R.id.img_seat) as ImageView
            imgSeatSelected = itemView.findViewById<View>(R.id.img_seat_selected) as ImageView
        }
    }

    private class CenterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgSeat: ImageView
        val imgSeatSelected: ImageView

        init {
            imgSeat = itemView.findViewById<View>(R.id.img_seat) as ImageView
            imgSeatSelected = itemView.findViewById<View>(R.id.img_seat_selected) as ImageView
        }
    }

    private class EmptyViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    )

    private val mLayoutInflater: LayoutInflater
    private val mItems: List<AbstractItem>
    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun getItemViewType(position: Int): Int {
        return mItems[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == AbstractItem.TYPE_EDGE) {
            val itemView = mLayoutInflater.inflate(R.layout.list_item_seat_upper, parent, false)
            EdgeViewHolder(itemView)
        } else {
            val itemView = View(mContext)
            EmptyViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val type = mItems[position].type
        if (type == AbstractItem.TYPE_CENTER) {
            val item = mItems[position] as CenterItem
            val holder = viewHolder as CenterViewHolder
            holder.imgSeat.setOnClickListener {
                toggleSelection(position)
                mOnSeatSelected.onSeatSelected(selectedItemCount)
            }
            holder.imgSeatSelected.visibility =
                if (isSelected(position)) View.VISIBLE else View.INVISIBLE
        } else if (type == AbstractItem.TYPE_EDGE) {
            val item = mItems[position] as EdgeItem
            val holder = viewHolder as EdgeViewHolder
            holder.imgSeat.setOnClickListener {
                toggleSelection(position)
                mOnSeatSelected.onSeatSelected(selectedItemCount)
            }
            holder.imgSeatSelected.visibility =
                if (isSelected(position)) View.VISIBLE else View.INVISIBLE
        }
    }

    init {
        mLayoutInflater = LayoutInflater.from(mContext)
        mItems = items
    }
}