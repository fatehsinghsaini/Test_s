package com.os.busservice.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.os.busservice.R
import kotlinx.android.synthetic.main.image_pager_adapter.view.*


class ImagePagerAdapter(var mActivity:Context,var listItem:ArrayList<String>) :RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.image_pager_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Glide.with(mActivity)
            .load(listItem[position]).placeholder(R.mipmap.ic_launcher)
            .into(holder.itemView.image)

        holder.itemView.image.setOnClickListener {
            /* mActivity.startActivity(
                 Intent(mActivity, FullImageViewActivity::class.java).putExtra(
                     "Tags.DATA",
                     listItem
                 )
             )*/
        }
    }

    override fun getItemCount()  =listItem.size

    open class ViewHolder(view: View):RecyclerView.ViewHolder(view)
}