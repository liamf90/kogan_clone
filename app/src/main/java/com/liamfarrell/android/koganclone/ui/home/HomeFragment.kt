package com.liamfarrell.android.koganclone.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.adapters.*
import com.liamfarrell.android.koganclone.di.Injectable
import com.liamfarrell.android.koganclone.model.homescreen.HomeScreenItems
import com.liamfarrell.android.koganclone.ui.activity.OpenCloseBottomNavigation
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var headerRecyclerView : RecyclerView
    private lateinit var currentDealsRecyclerView : RecyclerView
    private lateinit var featuredBrandsRecyclerView : RecyclerView
    private lateinit var popularCategoriesRecyclerView : RecyclerView


    val adRetriever =
        HomeScreenItems()

    val headerImagesResourceIdList =
        listOf(R.drawable.in_high_demand, R.drawable.covid_19, R.drawable.led_tvs, R.drawable.home_office_essentials, R.drawable.fitness_equipment, R.drawable.cold_weather_essentials)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel= ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        setupHeader(root)
        setupCurrentDeals(root)
        setupPopularCategories(root)
        setupFeaturedBrands(root)
        val trendingProductsListAdapter = TrendingProductsPagedListAdapter()
        setupTrendingProducts(root, trendingProductsListAdapter)
        setupToolbar(root.findViewById(R.id.toolbar))
        root.findViewById<TextView>(R.id.currentDealsTitleTextView).setOnClickListener {


        }
        subscribeUi(trendingProductsListAdapter)
        return root
    }

    private fun subscribeUi(adapter: TrendingProductsPagedListAdapter){
        homeViewModel.notifications.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })

        homeViewModel.networkErrors.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }


    private fun setupToolbar(toolbar: Toolbar){
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    override fun onResume() {
        (activity as OpenCloseBottomNavigation).openBottomNavigation()
        headerRecyclerView.scrollToPosition(0)
        currentDealsRecyclerView.scrollToPosition(0)
        featuredBrandsRecyclerView.scrollToPosition(0)
        popularCategoriesRecyclerView.scrollToPosition(0)
        super.onResume()
    }


    private fun setupHeader(root: View){
        headerRecyclerView = root.findViewById<RecyclerView>(R.id.header_recycler_view)
        val adapter = HeaderListAdapter()
        headerRecyclerView.adapter = adapter
        adapter.submitList(adRetriever.getHeaderItems())
        root.findViewById<IndefinitePagerIndicator>(R.id.recyclerview_pager_indicator).attachToRecyclerView(headerRecyclerView)
    }

    private fun setupCurrentDeals(root: View){
        currentDealsRecyclerView = root.findViewById<RecyclerView>(R.id.current_deals_recycler_view)
        val adapter = CurrentDealsAndPopularCategoriesListAdapter()
        currentDealsRecyclerView.adapter = adapter
        adapter.submitList(adRetriever.getCurrentDealItems())
    }

    private fun setupPopularCategories(root: View){
        popularCategoriesRecyclerView = root.findViewById<RecyclerView>(R.id.popular_categories_recycler_view)
        val adapter = CurrentDealsAndPopularCategoriesListAdapter()
        popularCategoriesRecyclerView.adapter = adapter
        adapter.submitList(adRetriever.getPopularCategories())
    }

    private fun setupFeaturedBrands(root: View){
        featuredBrandsRecyclerView = root.findViewById<RecyclerView>(R.id.featured_brands_recycler_view)
        val adapter = FeaturedBrandsListAdapter()
        featuredBrandsRecyclerView.adapter = adapter
        adapter.submitList(adRetriever.getBrandItems())
    }

    private fun setupTrendingProducts(root: View, adapter: TrendingProductsPagedListAdapter){
        val headerRecyclerView = root.findViewById<RecyclerView>(R.id.trending_products_recycler_view)
        headerRecyclerView.adapter = adapter
        headerRecyclerView.isNestedScrollingEnabled = false
        //adapter.submitList(adRetriever.getTrendingItems())
    }




}
