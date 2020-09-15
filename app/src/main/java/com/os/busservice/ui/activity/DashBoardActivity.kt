package com.os.busservice.ui.activity
import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.os.busservice.R
import com.os.busservice.data.ApiResponse
import com.os.busservice.databinding.ActivityDashboardBinding
import com.os.busservice.model.CommonResponse
import com.os.busservice.ui.baseFile.ActivityFromFragmentCallack
import com.os.busservice.ui.baseFile.BaseBindingActivity
import com.os.busservice.ui.fragment.HomeFragment
import com.os.busservice.ui.fragment.MyAccountFragment
import com.os.busservice.ui.viewModel.LoginViewModel
import com.os.busservice.utility.AppDelegate
import com.os.busservice.utility.ProgressDialog
import com.os.busservice.utility.Tags
import com.os.busservice.utility.Tags.FAQ
import com.os.busservice.utility.UtilityMethods
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.toolbar.*


class DashBoardActivity : BaseBindingActivity(), ActivityFromFragmentCallack,View.OnClickListener {
    private var mNavViewHeader: View?=null
    private var mBinding: ActivityDashboardBinding?=null
    private var mLoginViewModel: LoginViewModel?=null

    override fun setBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard)
        mBinding?.sessionManager = sessionManager
    }

    override fun createActivityObject() {
        mLoginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        mLoginViewModel?.logoutLiveData?.observe(this, Observer {
            handleLogoutApi(it)
        })
    }

    override fun initializeObject() {
        mActivity = this

        if (intent.hasExtra(Tags.DATA)) {
            when (intent.getStringExtra(Tags.DATA)) {
                Tags.ORDERS -> {
                    toolbarName.text =getString(R.string.app_name)
                    addressLL.visibility=View.GONE
                    bottom_navigation.selectedItemId = R.id.navigation_order
                    changeFragment(HomeFragment(), false)
                }
            }

        } else{
            addressLL.visibility=View.VISIBLE
            toolbarName.visibility  =View.GONE
            bottom_navigation.selectedItemId = R.id.navigation_home
            changeFragment(HomeFragment(), false)
        }
    }

    override fun setListeners() {
        bottom_navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        //bind with navigation view
        mNavViewHeader = navigationView.getHeaderView(0)
        navigationView.bringToFront()
//        val navigationBinding = NavigationMenuBinding.bind(mNavViewHeader!!)
        //navigationBinding.logout.setOnClickListener(this)
        //navigationBinding.aboutUs.setOnClickListener(this)
       // navigationBinding.contactUs.setOnClickListener(this)
      //  navigationBinding.help.setOnClickListener(this)
      //  navigationBinding.faq.setOnClickListener(this)
       // navigationBinding.notificationList.setOnClickListener(this)

        /* R.id.mAddress -> startActivity(Intent(this, ChangeCurrentLocation::class.java))
            R.id.about_us -> mCallStaticPage(getString(R.string.about_us),ABOUT_US)
            R.id.contactUs -> mCallStaticPage(getString(R.string.contact_us), CONTACT_US)
            R.id.faq -> mCallStaticPage(getString(R.string.faq),FAQ)*/

/*        navigationBinding.home.setOnClickListener(View.OnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            navigationBinding.home.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            navigationBinding.home.setTextColor(resources.getColor(R.color.textWhite))

            navigationBinding.notificationList.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.notificationList.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.logout.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.logout.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.aboutUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.aboutUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.contactUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.contactUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.help.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.help.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.faq.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.faq.setTextColor(resources.getColor(R.color.colorLightBlack))

            //startActivity(Intent(this, DashBoardActivity::class.java))

        })*/

       /* navigationBinding.notificationList.setOnClickListener(View.OnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            navigationBinding.notificationList.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            navigationBinding.notificationList.setTextColor(resources.getColor(R.color.textWhite))

            navigationBinding.home.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.home.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.logout.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.logout.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.aboutUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.aboutUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.contactUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.contactUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.help.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.help.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.faq.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.faq.setTextColor(resources.getColor(R.color.colorLightBlack))

//            startActivity(Intent(this, NotificationActivity::class.java))

        })*/

/*
        navigationBinding.faq.setOnClickListener(View.OnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            navigationBinding.faq.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            navigationBinding.faq.setTextColor(resources.getColor(R.color.textWhite))

            navigationBinding.home.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.home.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.logout.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.logout.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.aboutUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.aboutUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.contactUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.contactUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.help.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.help.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.notificationList.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.notificationList.setTextColor(resources.getColor(R.color.colorLightBlack))

//            mCallStaticPage(getString(R.string.faq),FAQ)

        })

        navigationBinding.contactUs.setOnClickListener(View.OnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            navigationBinding.contactUs.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            navigationBinding.contactUs.setTextColor(resources.getColor(R.color.textWhite))

            navigationBinding.home.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.home.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.logout.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.logout.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.aboutUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.aboutUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.faq.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.faq.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.help.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.help.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.notificationList.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.notificationList.setTextColor(resources.getColor(R.color.colorLightBlack))

//            mCallStaticPage(getString(R.string.contact_us), CONTACT_US)

        })

        navigationBinding.aboutUs.setOnClickListener(View.OnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            navigationBinding.aboutUs.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            navigationBinding.aboutUs.setTextColor(resources.getColor(R.color.textWhite))

            navigationBinding.home.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.home.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.logout.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.logout.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.contactUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.contactUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.faq.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.faq.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.help.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.help.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.notificationList.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.notificationList.setTextColor(resources.getColor(R.color.colorLightBlack))

//            mCallStaticPage(getString(R.string.about_us),ABOUT_US)

        })

        navigationBinding.logout.setOnClickListener(View.OnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            navigationBinding.logout.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            navigationBinding.logout.setTextColor(resources.getColor(R.color.textWhite))

            navigationBinding.home.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.home.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.aboutUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.aboutUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.contactUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.contactUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.faq.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.faq.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.help.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.help.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.notificationList.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.notificationList.setTextColor(resources.getColor(R.color.colorLightBlack))

            AppDelegate.showAlert(
                mActivity!!,
                getString(R.string.logout),
                getString(R.string.want_to_logout),
                this
            )

        })

        navigationBinding.help.setOnClickListener(View.OnClickListener {

            drawerLayout.closeDrawer(GravityCompat.START)

            navigationBinding.help.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            navigationBinding.help.setTextColor(resources.getColor(R.color.textWhite))

            navigationBinding.home.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.home.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.aboutUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.aboutUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.contactUs.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.contactUs.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.faq.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.faq.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.logout.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.logout.setTextColor(resources.getColor(R.color.colorLightBlack))

            navigationBinding.notificationList.setBackgroundColor(resources.getColor(R.color.textWhite))
            navigationBinding.notificationList.setTextColor(resources.getColor(R.color.colorLightBlack))

//            mCallStaticPage(getString(R.string.help), HELP)


        })*/

        toolbarMenu.setOnClickListener(this)
        mAddress.setOnClickListener(this)

      /*  navigationBinding.name.text = sessionManager?.fullName
        navigationBinding.session =sessionManager
        navigationBinding.profileLayout.setOnClickListener(this)*/

    }

    private var navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                addressLL.visibility=View.VISIBLE
                toolbarName.visibility = View.GONE
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

                when (item.itemId) {
                    R.id.navigation_home -> {
                        toolbarName.text =getString(R.string.home)
                        addressLL.visibility=View.VISIBLE
                        toolbarName.visibility = View.GONE
                        changeFragment(HomeFragment(),false)
                        return true
                    }
                    R.id.navigation_order -> {

                        toolbarName.text =getString(R.string.booking)
                        addressLL.visibility=View.GONE
                        toolbarName.visibility = View.VISIBLE

                        changeFragment(HomeFragment(),false)
                        return true
                    }
                    R.id.navigation_offer -> {

                        return true
                    }
             /*       R.id.navigation_fav -> {

                        toolbarName.text =getString(R.string.favorite)
                        addressLL.visibility=View.GONE
                        toolbarName.visibility = View.VISIBLE

                        changeFragment(HomeFragment(),false)
                        return true
                    }*/
                    R.id.navigation_account -> {

                        toolbarName.text =getString(R.string.my_account)
                        addressLL.visibility=View.GONE
                        toolbarName.visibility = View.VISIBLE

                        changeFragment(MyAccountFragment(),false)
                        return true
                    }
                }
                return false
            }
        }

    override fun onClick(p0: View?) {
        drawerLayout.closeDrawer(GravityCompat.START)
        if (p0 == null) {
          /*  mLoginViewModel?.logoutApi(
                sessionManager?.loginResult?.id
            )*/
            return
        }

        when(p0.id){
        /*    R.id.logout ->{
                AppDelegate.showAlert(
                    mActivity!!,
                    getString(R.string.logout),
                    getString(R.string.want_to_logout),
                    this
                )
            }*/
            R.id.toolbarMenu ->
                drawerLayout.openDrawer(GravityCompat.START)
       /*     R.id.mAddress -> startActivity(Intent(this, ChangeCurrentLocation::class.java))
            R.id.about_us -> mCallStaticPage(getString(R.string.about_us),ABOUT_US)
            R.id.contactUs -> mCallStaticPage(getString(R.string.contact_us), CONTACT_US)
            R.id.faq -> mCallStaticPage(getString(R.string.faq),FAQ)
            R.id.help -> mCallStaticPage(getString(R.string.help), HELP)
            R.id.notification_list ->  startActivity(Intent(this, NotificationActivity::class.java))
            R.id.profileLayout -> startActivity(Intent(this, EditProfileActivity::class.java))*/

        }
    }

    private fun mCallStaticPage(mTitle:String?, slag:String?){
        startActivity(
            Intent(
                mActivity,
                StaticPageActivity::class.java
            ).putExtra(Tags.TITLE, mTitle).putExtra(Tags.DATA,slag )
                .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        )
    }

    private fun handleLogoutApi(result: ApiResponse<CommonResponse>?) {
        when (result!!.status) {
            ApiResponse.Status.ERROR -> {
                ProgressDialog.hideProgressDialog()
                UtilityMethods.showToastMessage(
                    mActivity!!,
                    getString(R.string.error_network_connection)
                )
            }
            ApiResponse.Status.LOADING -> ProgressDialog.showProgressDialog(mActivity!!)
            ApiResponse.Status.SUCCESS -> {
                ProgressDialog.hideProgressDialog()
                if (result.data!!.success) {
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)
                    cacheDir.deleteRecursively()
                    sessionManager?.mLogout()

                    startActivity(
                        Intent(
                            this,
                            LoginActivity::class.java
                        ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    )
                    finish()


                } else
                    UtilityMethods.showToastMessage(mActivity!!, result.data.msg)

            }

        }
    }

    override fun changeFragment(fragment: Fragment, isAddToBackStack: Boolean) {

    }

}