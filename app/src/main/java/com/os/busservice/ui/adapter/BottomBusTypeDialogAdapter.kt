package com.os.busservice.ui.adapter
import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.os.busservice.R
import com.os.busservice.databinding.AddressItemBinding
import com.os.busservice.databinding.BottomSeatViewBinding
import com.os.busservice.databinding.BusSearchItemBinding
import com.os.busservice.databinding.RecentBookingItemBinding
import com.os.busservice.listeners.AddressListener
import com.os.busservice.listeners.BusSearchListener
import com.os.busservice.listeners.CommonListener
import com.os.busservice.model.address.AddressResult
import com.os.busservice.model.busListResponse.BusRouteData
import com.os.busservice.model.busListResponse.BusType

class BottomBusTypeDialogAdapter(var mCartListener: BusSearchListener,var mList:ArrayList<BusType>,val mContext:Context) : RecyclerBaseAdapter() {

    var mPos = 0

    override fun getLayoutIdForPosition(position: Int) = R.layout.bottom_seat_view

    override fun putViewDataBinding(binding: ViewDataBinding) {
        if (binding is BottomSeatViewBinding) {
            binding.mItem =mList[mPos]
           when(mPos){
               0-> binding.tvName.setCompoundDrawables(null,ContextCompat.getDrawable(mContext,R.drawable.ic_air_conditioner),null,null)
               1-> binding.tvName.setCompoundDrawables(null,ContextCompat.getDrawable(mContext,R.drawable.ic_non_ac),null,null)
               2-> binding.tvName.setCompoundDrawables(null,ContextCompat.getDrawable(mContext,R.drawable.ic_noun_bus_seat),null,null)
               3-> binding.tvName.setCompoundDrawables(null,ContextCompat.getDrawable(mContext,R.drawable.ic_air_conditioner),null,null)

           }

        }

    }

    override fun getViewModel(position: Int) {
        mList[position]
        mPos = position
    }

    override fun getItemCount(): Int = mList.size

}