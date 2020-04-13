package com.liamfarrell.android.koganclone.ui.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.*
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.glide.slider.library.slidertypes.BaseSliderView
import com.glide.slider.library.tricks.ViewPagerEx
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.adapters.SliderAdapter
import com.liamfarrell.android.koganclone.databinding.ProductFragmentBinding
import com.liamfarrell.android.koganclone.di.Injectable
import com.liamfarrell.android.koganclone.model.Product
import com.liamfarrell.android.koganclone.model.SliderItem
import com.liamfarrell.android.koganclone.model.addItemToCartCallback
import com.liamfarrell.android.koganclone.ui.activity.OpenCloseBottomNavigation
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.product_fragment.*
import javax.inject.Inject


class ProductFragment : Fragment(), Injectable, BaseSliderView.OnSliderClickListener,
    ViewPagerEx.OnPageChangeListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProductViewModel
    private lateinit var adapter: SliderAdapter



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setHasOptionsMenu(true)
        val productId = arguments!!.getInt("productId")
        val binding = ProductFragmentBinding.inflate(inflater, container, false)
        setupToolbar(binding.includeToolbar.findViewById(R.id.toolbar))
        viewModel= ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)
        viewModel.getProduct(productId)
        adapter = SliderAdapter(context)
        binding.slider.setSliderAdapter(adapter)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.addToCartButton.root.setOnClickListener  {
            shoppingCartAddImageAnimation()
            Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
        }
        binding.categoriesTextView.movementMethod = LinkMovementMethod.getInstance()
        subscribeUi(binding)
        return binding.root
    }


    private fun subscribeUi(binding : ProductFragmentBinding){
        viewModel.product.observe(viewLifecycleOwner, Observer { product ->
            binding.product = product
            (activity as AppCompatActivity).supportActionBar?.title = product.title

            product.imageUrl?.let{ imageUrlList -> adapter.renewItems(imageUrlList.map { SliderItem(it)})}
            setupWebViewSections(product)
        })

        viewModel.spinner.observe(viewLifecycleOwner, Observer {
            binding.spinner = it
        })
    }

    private fun setupToolbar(toolbar: Toolbar){
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebViewSections(product: Product){
        val descriptionUrl = product.htmlSectionUrlsMap?.get("Description")
        if (descriptionUrl != null) {
            descriptionWebView.settings.javaScriptEnabled = true
            descriptionWebView.webChromeClient = WebChromeClient()
            descriptionWebView.loadUrl(descriptionUrl)
        }

        val specificationsUrl = product.htmlSectionUrlsMap?.get("Specifications")
        if (specificationsUrl != null) {
            specificationsWebView.settings.javaScriptEnabled = true
            specificationsWebView.webChromeClient = WebChromeClient()
            specificationsWebView.loadUrl(specificationsUrl)
        }
    }

    private fun shoppingCartAddImageAnimation(){
        addToCartConstraintLayout.visibility = View.INVISIBLE
        ghostItemImageView.visibility = View.VISIBLE


        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width = displayMetrics.widthPixels

        val animationToBottom = AnimationSet(true)
        animationToBottom.interpolator = AccelerateInterpolator()

        val scaleToBottomAnimation = ScaleAnimation(
            1.toFloat(),
            0.1.toFloat(),
            1.toFloat(),
            0.1.toFloat()
        )
        scaleToBottomAnimation.duration = 1000
        animationToBottom.addAnimation(scaleToBottomAnimation)

        val translateToShoppingCartAnimation = TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, width.toFloat(), Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, 0f)
        translateToShoppingCartAnimation.duration = 1000
        translateToShoppingCartAnimation.zAdjustment = Animation.ZORDER_TOP

        animationToBottom.addAnimation(translateToShoppingCartAnimation)
        val alphaToBottom = AlphaAnimation(0.8.toFloat(), 0.toFloat())
        alphaToBottom.duration = 1000
        animationToBottom.addAnimation(alphaToBottom)
        animationToBottom.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                ghostItemImageView.visibility = View.GONE
                addToCartConstraintLayout.visibility = View.VISIBLE
                viewModel.addItemToShoppingCart()
                (activity as addItemToCartCallback).onItemAddedToCart()
            }
        })
        ghostItemImageView.startAnimation(animationToBottom)

    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        descriptionHeader.setOnClickListener {
            if (descriptionWebView.visibility == View.VISIBLE){
                descriptionWebView.visibility = View.GONE
                descriptionArrowImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right_24px, null))
            } else {
                descriptionWebView.visibility = View.VISIBLE
                descriptionArrowImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_down_24px, null))
            }
        }

        specificationsHeader.setOnClickListener {
            if (specificationsWebView.visibility == View.VISIBLE){
                specificationsWebView.visibility = View.GONE
                specificationsArrowImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_right_24px, null))
            } else {
                specificationsWebView.visibility = View.VISIBLE
                specificationsArrowImageView.setImageDrawable(resources.getDrawable(R.drawable.ic_keyboard_arrow_down_24px, null))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.product_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.navigation_search -> {

            }
            R.id.navigation_share -> {

            }
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        (activity as OpenCloseBottomNavigation).closeBottomNavigation()
        super.onResume()
    }

    override fun onSliderClick(slider: BaseSliderView?) {
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
    }

}
