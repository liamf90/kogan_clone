package com.liamfarrell.android.koganclone.ui.menu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.liamfarrell.android.koganclone.R
import com.liamfarrell.android.koganclone.ui.activity.OpenCloseBottomNavigation
import kotlinx.android.synthetic.main.fragment_more_menu.*
import kotlinx.android.synthetic.main.toolbar_logo.*


class MoreMenuFragment : Fragment(R.layout.fragment_more_menu) {

    companion object{
        val CONTACT_CUSTOMER_CARE_URL = "http://help.kogan.com/hc/en-us"
        val TERMS_AND_CONDITIONS_URL = "http://kogan.com/au/tcs-landing"
        val PRIVACY_URL = "http://kogan.com/au/privacy-policy"

        val FEEDBACK_EMAIL = "app-feedback.android@kogan.com"
        val FEEDBACK_EMAIL_SUBJECT= "Android app feedback"
    }

    private var emailSent = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        accountMenuLayout.setOnClickListener {
            //Navigate
        }
        orderLayout.setOnClickListener {
            //Navigate
        }
        shopByDepartmentLayout.setOnClickListener {
            //Navigate
        }
        shopByBrandsLayout.setOnClickListener {
            //Navigate
        }
        wishListLayout.setOnClickListener {
            //Navigate
        }
        shoppingCartLayout.setOnClickListener {
            //Navigate
        }
        contactCustomerCareLayout.setOnClickListener {
           openUriInBrowser(CONTACT_CUSTOMER_CARE_URL)
        }
        termsAndConditionsLayout.setOnClickListener {
            openUriInBrowser(TERMS_AND_CONDITIONS_URL)
        }
        privacyLayout.setOnClickListener {
            openUriInBrowser(PRIVACY_URL)
        }
        helpImproveAppLayout.setOnClickListener {
            sendEmail()
        }

        setupToolbar(toolbar)


        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupToolbar(toolbar: Toolbar){
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }


    private fun openUriInBrowser(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        startActivity(openURL)
    }

    private fun sendEmail(){
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf(FEEDBACK_EMAIL))
        i.putExtra(Intent.EXTRA_SUBJECT, FEEDBACK_EMAIL_SUBJECT)
        try {
            startActivity(Intent.createChooser(i, getString(R.string.complete_action_using)))
            emailSent = true
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(context, R.string.no_email_clients, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        (activity as OpenCloseBottomNavigation).openBottomNavigation()
        if (emailSent){
            Toast.makeText(context, R.string.thankyou_for_feedback, Toast.LENGTH_SHORT).show()
            emailSent = false }
        super.onResume()
    }



}



