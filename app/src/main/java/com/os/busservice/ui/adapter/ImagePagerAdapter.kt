package com.os.busservice.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.os.busservice.R
import kotlinx.android.synthetic.main.image_pager_adapter.view.*


class ImagePagerAdapter(var mActivity:Context,var listItem:ArrayList<String>) :PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.image_pager_adapter, container, false)
        container.addView(view)

        Glide.with(mActivity)
            .load(listItem[position]).placeholder(R.mipmap.ic_launcher)
            .into(view.image)

        view.image.setOnClickListener {
           /* mActivity.startActivity(
                Intent(mActivity, FullImageViewActivity::class.java).putExtra(
                    "Tags.DATA",
                    listItem
                )
            )*/
        }

        return view
    }
    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun getCount(): Int =listItem.size
    fun mUpdateImage(mList:ArrayList<String>){
        listItem=mList
    }
}