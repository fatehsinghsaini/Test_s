package com.os.busservice.ui.baseFile


import androidx.fragment.app.Fragment

interface ActivityFromFragmentCallack {
    fun changeFragment(fragment: Fragment, isAddToBackStack: Boolean)

    fun changeToolbarTitle(title: String)
}
