package com.os.busservice.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.os.busservice.R
import com.os.busservice.model.zoneList.ZoneResult


class ZoneListAdapter(
    internal var context: Context,
    private var zoneArrayList: List<ZoneResult>, var listner:ClickListener, val type:String
) : RecyclerView.Adapter<ZoneListAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       val items= zoneArrayListfilter!![position]

        val name=items.name


        holder.txt_c_listvalue!!.text = name

        holder.txt_c_listvalue!!.setOnClickListener {

            listner.mSearchItemClick(items,type)

        /*    countryTxt.text = items.Country.country_code
            city.text=items.City.name

            if(dialog.isShowing)
                dialog.dismiss()*/

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.zone_list_adapter, parent, false))
    }

    private var zoneArrayListfilter: MutableList<ZoneResult>? = null

    init {
        if (this.zoneArrayListfilter == null)
            this.zoneArrayListfilter = ArrayList()
        zoneArrayListfilter!!.addAll(zoneArrayList)
    }

    override fun getItemCount(): Int {
        return zoneArrayListfilter!!.size
    }

    fun getItem(position: Int): ZoneResult {
        return zoneArrayListfilter!![position]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var txt_c_listvalue: TextView? = null

        init {
            txt_c_listvalue = view.findViewById(R.id.txt_c_listvalue)
        }
    }

    fun updateList(list: MutableList<ZoneResult>){
        this.zoneArrayListfilter=list
    }

    interface ClickListener{
        fun mSearchItemClick(item:ZoneResult,type:String)
    }


}