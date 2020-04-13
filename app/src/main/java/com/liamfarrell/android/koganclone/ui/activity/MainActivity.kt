package com.liamfarrell.android.koganclone.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.di.Injectable
import com.liamfarrell.android.koganclone.model.addItemToCartCallback
import com.liamfarrell.android.koganclone.ui.home.HomeHostFragment
import com.liamfarrell.android.koganclone.ui.menu.MoreMenuHostFragment
import com.liamfarrell.android.koganclone.ui.notifications.NotificationsHostFragment
import com.liamfarrell.android.koganclone.ui.shoppingcart.EditOrderItemQuantityDialog
import com.liamfarrell.android.koganclone.util.zoomInAnimation
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject



interface OpenCloseBottomNavigation{
    fun closeBottomNavigation()
    fun openBottomNavigation()
}


class MainActivity : AppCompatActivity(), HasAndroidInjector, Injectable, OpenCloseBottomNavigation, addItemToCartCallback, EditOrderItemQuantityDialog.NoticeDialogListener {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: MainViewModel
    private lateinit var shoppingCartMenuLayout : ConstraintLayout
    private lateinit var navView : BottomNavigationView


    //The selected nav view fragment. Home = 0, Notification = 1, MoreMenu = 2
    private var bottomNavViewSelectedFragment : Int = 0

    private val homeFragment : HomeHostFragment by lazy { HomeHostFragment() }
    private val notificationFragment : NotificationsHostFragment by lazy { NotificationsHostFragment() }
    private val moreMenuFragment : MoreMenuHostFragment by lazy { MoreMenuHostFragment() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId = R.id.home_menu_item
        viewModel= ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }


    //Observe the shopping cart count once the menu has been created
    private fun subscribeUi(){
        viewModel.shoppingCartCount.observe(this, Observer {
            val shoppingCartCountTextView = shoppingCartMenuLayout.findViewById<TextView>(R.id.shoppingCartCounTextview)
            if (it == null || it == 0){
                shoppingCartCountTextView.visibility = View.GONE
            } else {
                shoppingCartCountTextView.text = it.toString()
                shoppingCartCountTextView.visibility = View.VISIBLE
            }
        })

    }


    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.primaryNavigationFragment?.let{transaction.hide(it)}
        if (supportFragmentManager.fragments.contains(fragment)){
            transaction.show(fragment)
        } else {
            transaction.add(R.id.nav_host_fragment, fragment) }
        transaction.setPrimaryNavigationFragment(fragment)
        transaction.commit()
    }


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.home_menu_item -> {
                bottomNavViewSelectedFragment = 0
                loadFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.notifications_menu_item -> {
                bottomNavViewSelectedFragment = 1
                loadFragment(notificationFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.more_menu_item -> {
                bottomNavViewSelectedFragment = 2
                loadFragment(moreMenuFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        shoppingCartMenuLayout = menu!!.findItem(R.id.navigation_shopping_cart).actionView.findViewById(R.id.badge_layout1)
        shoppingCartMenuLayout.setOnClickListener {
            Timber.i("Shopping Cart Menu Item On Click")
            when(bottomNavViewSelectedFragment){
                0 -> Navigation.findNavController(this,
                    R.id.nav_home_fragment
                ).navigate(R.id.shoppingCartFragment)
                1 -> Navigation.findNavController(this,
                    R.id.nav_notification_fragment
                ).navigate(R.id.shoppingCartFragment)
                2 -> Navigation.findNavController(this,
                    R.id.nav_more_menu_fragment
                ).navigate(R.id.shoppingCartFragment)
            }

        }
        subscribeUi()
        return true
    }


    override fun androidInjector() = dispatchingAndroidInjector

    override fun onItemAddedToCart() {
        shoppingCartMenuLayout.zoomInAnimation()
    }

    override fun onDialogRemoveItemClick(productId: Int) {
        viewModel.removeProductFromShoppingCart(productId)
    }

    override fun onDialogDoneItemClick(productId: Int, finalNumber: Int) {
        viewModel.changeProductShoppingCartQuantity(productId, finalNumber)
    }




    override fun closeBottomNavigation() {
        navView.visibility = View.GONE
    }

    override fun openBottomNavigation() {
        navView.visibility = View.VISIBLE
    }
}
