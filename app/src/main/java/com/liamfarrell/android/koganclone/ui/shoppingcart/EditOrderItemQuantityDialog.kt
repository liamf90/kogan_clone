package com.liamfarrell.android.koganclone.ui.shoppingcart

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.liamfarrell.android.koganclone.R

class EditOrderItemQuantityDialog : DialogFragment() {

    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogRemoveItemClick(productId: Int)
        fun onDialogDoneItemClick(productId: Int, finalNumber: Int)
    }

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            // The activity doesn't implement the interface, throw exception
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val productId = arguments!!.getInt("productId")
            val initialProductCount = arguments!!.getInt("initialProductCount")
            val title = arguments!!.getString("title")
            val imageUrl = arguments!!.getString("imageUrl")

            // Get the layout inflater
            val inflater = requireActivity().layoutInflater
            val rootView = inflater.inflate(R.layout.dialog_edit_shopping_cart_item, null)
            val titleTextView = rootView.findViewById<TextView>(R.id.titleTextView)
            titleTextView.text = title
            val numberTextView = rootView.findViewById<TextView>(R.id.numberTextView)
            numberTextView.text = initialProductCount.toString()
            val increaseButton = rootView.findViewById<ImageButton>(R.id.increaseCountButton)
            increaseButton.setOnClickListener {
                val currentNum = Integer.valueOf(numberTextView.text.toString())
                numberTextView.text = (currentNum + 1).toString()
            }
            val decreaseButton = rootView.findViewById<ImageButton>(R.id.decreaseCountButton)
            decreaseButton.setOnClickListener {
                val currentNum = Integer.valueOf(numberTextView.text.toString())
                if (currentNum != 0){
                numberTextView.text = (currentNum - 1).toString()}
            }

            val imageView = rootView.findViewById<ImageView>(R.id.productImageView)
            Glide.with(rootView.context).load(imageUrl).into(imageView)


            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(rootView)
                // Add action buttons
                .setPositiveButton(
                    "DONE",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Make changes
                        listener.onDialogDoneItemClick(productId, Integer.valueOf(numberTextView.text.toString()))
                        dialog.dismiss()
                    })
                .setNegativeButton("REMOVE",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Remove all items
                        listener.onDialogRemoveItemClick(productId)
                        dialog.dismiss()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}