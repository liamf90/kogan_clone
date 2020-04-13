package com.liamfarrell.android.koganclone.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.adapters.NotificationPagedListAdapter
import com.liamfarrell.android.koganclone.di.Injectable
import com.liamfarrell.android.koganclone.model.getErrorMessage
import com.liamfarrell.android.koganclone.ui.activity.OpenCloseBottomNavigation
import javax.inject.Inject

class NotificationsFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var notificationList: RecyclerView
    private lateinit var swipeContainer : SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel = ViewModelProvider(this, viewModelFactory).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val adapter = NotificationPagedListAdapter()
        notificationList = root.findViewById(R.id.notification_list)
        notificationList.adapter = adapter
        notificationList.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        swipeContainer = root.findViewById(R.id.swipe_container)
        swipeContainer.setOnRefreshListener {onSwipeToRefresh()}
        subscribeUi(adapter)
        setupToolbar(root.findViewById(R.id.toolbar))
        return root
    }

    private fun subscribeUi(adapter: NotificationPagedListAdapter){
        notificationsViewModel.notifications.observe(viewLifecycleOwner, Observer { adapter.submitList(it) })

        notificationsViewModel.spinner.observe(viewLifecycleOwner, Observer {
            swipeContainer.isRefreshing = it
        })

        notificationsViewModel.networkErrors.observe(viewLifecycleOwner, Observer {
            it?.let{Toast.makeText(context, getErrorMessage(requireContext(), it), Toast.LENGTH_SHORT).show()}
        })
    }

    private fun setupToolbar(toolbar: Toolbar){
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun onSwipeToRefresh(){
        notificationsViewModel.checkForUpdates()
    }

    override fun onResume() {
        (activity as OpenCloseBottomNavigation).openBottomNavigation()
        super.onResume()
    }

}
