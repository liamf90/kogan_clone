package com.liamfarrell.android.koganclone.model


import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.liamfarrell.android.koganclone.R
import timber.log.Timber

class  Product constructor(
    @SerializedName("product_id") var productId: Int = -1,
    var title: String = "",
    @Ignore val description: String = "",
    var brand: String = "",
    @Ignore val department: String = "",
    @Ignore val category: String = "",
    @SerializedName("kogan_first") var isKoganFirstEligible: Boolean = false,
    @SerializedName("free_shipping") var freeShipping:Boolean = false,
    @SerializedName("leaves_warehouse") @Ignore var leavesWarehouse: String = "",
    var price: Double = 0.00,
    @SerializedName("was_price") var oldPrice: Double? = null,
    @SerializedName("html_section_urls")  @Ignore var htmlSectionUrlsMap: Map<String, String>? = null,
    @SerializedName("img_url") var imageUrl: List<String>? = null
) {


    fun getSavePrice(): Double?{
        return oldPrice?.let{it - price}
    }

    fun getQantasPointsAmount(): Int {
        return (price / 2.2).toInt()
    }

    fun getCategoriesClickableSpan(con: Context) : SpannableStringBuilder{
        val home = con.resources.getString(R.string.home_category)
        val categoriesSpan = SpannableStringBuilder()
        categoriesSpan.append(home)
        categoriesSpan.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                //On Home Clicked
                Timber.i("Home category clicked")
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            } }, 0, categoriesSpan.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        categoriesSpan.append(" / ")
        categoriesSpan.append(department)
        categoriesSpan.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                //On Home Clicked - Go to
                Timber.i("Main category clicked")
            } override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false}},
            categoriesSpan.length - department.length, categoriesSpan.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        categoriesSpan.append(" / ")
        categoriesSpan.append(category)
        categoriesSpan.setSpan(object: ClickableSpan() {
            override fun onClick(widget: View) {
                //On Home Clicked
                Timber.i("Sub category clicked")
            } override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false}},
            categoriesSpan.length - category.length, categoriesSpan.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return categoriesSpan
    }

}