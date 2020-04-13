package com.liamfarrell.android.koganclone.ui.shoppingcart

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.adapters.ShoppingCartListAdapter
import com.liamfarrell.android.koganclone.databinding.FragmentShoppingCartBinding
import com.liamfarrell.android.koganclone.di.Injectable
import com.liamfarrell.android.koganclone.model.getErrorMessage
import com.liamfarrell.android.koganclone.ui.activity.OpenCloseBottomNavigation
import timber.log.Timber
import javax.inject.Inject


class ShoppingCartFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ShoppingCartViewModel
    private lateinit var shoppingCartList: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ShoppingCartViewModel::class.java)
        val binding = FragmentShoppingCartBinding.inflate(inflater, container, false)

        val toolbar : Toolbar =  binding.includeToolbar.findViewById(R.id.toolbar)
        setupToolbar(toolbar)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.termsAndConditionsTextView.movementMethod = LinkMovementMethod.getInstance()
        val adapter = ShoppingCartListAdapter()
        shoppingCartList = binding.itemsInCartList
        shoppingCartList.adapter = adapter
        shoppingCartList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        subscribeUi(adapter)
        return binding.root
    }

    private fun subscribeUi(adapter: ShoppingCartListAdapter){
        viewModel.items.observe(viewLifecycleOwner, Observer {
          adapter.submitList(it)
        })

        viewModel.caughtError.observe(viewLifecycleOwner, Observer {
            it?.let{Toast.makeText(context, getErrorMessage(requireContext(), it), Toast.LENGTH_SHORT).show()}
        })
    }

    private fun setupToolbar(toolbar: Toolbar){
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.title = getString(R.string.cart_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.shopping_cart_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.navigation_search -> {

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
        activity?.actionBar?.title = getString(R.string.cart_toolbar)
        activity?.actionBar?.setHomeButtonEnabled(true)
        super.onResume()
    }



}
