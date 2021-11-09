package com.example.softlogistica.ui.edit_product

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.InverseMethod
import com.bumptech.glide.Glide
import com.example.softlogistica.R


object BindingUtilsEditProduct {


//    @BindingAdapter("bindDouble")
//    fun  bindPrice(textView: TextView, double: Double?){
//
//        if (double != null){
//            textView.text = double.toString()
//        }else{
//            textView.visibility = View.GONE
//        }
//    }

    object Converter {
        @InverseMethod("stringToInt")
        @JvmStatic fun intToString(value: Int?): String? {
            if(value != null){
                return value.toString()
            }else{
                return null
            }
        }

        @JvmStatic fun stringToInt(value: String?): Int? {
            if(value != null){
                return value.toInt()
            }else{
                return null
            }
        }
    }


    @BindingAdapter("app:timeAttrChanged")
    @JvmStatic fun setListeners(view: EditText, attrChange: InverseBindingListener) {

    }


    @BindingAdapter("bindFamily")
      fun setBindFamily(spinner: Spinner, familyId: Int) {

    }

    @InverseBindingAdapter(attribute = "bindFamily")
    @JvmStatic fun getBindFamily(spinner: Spinner): Int {

        if(spinner.selectedItem.toString()==("Maquinaria")){
            return 1
        }else if(spinner.selectedItem.toString()==("Medios Auxiliares")){
            return 2
        }else if(spinner.selectedItem.toString()==("Trasnporte")){
            return 3
        }else{
            return 0
        }

    }

    @BindingAdapter("bindFamilyAttrChanged")
    @JvmStatic fun setListener(spinner: Spinner, listener: InverseBindingListener?) {
        spinner.onFocusChangeListener = View.OnFocusChangeListener { focusedView, hasFocus ->
            if(hasFocus){

            }else{
                listener?.onChange()
            }

        }
    }



    @BindingAdapter("imgSrc")
    @JvmStatic fun imgSrc(imageView: ImageView, img: String?) {
        if (img == null) {
            imageView.setImageResource(R.drawable.ic_menu_gallery)
            imageView.visibility = View.GONE
        } else {
            val imgUri = img.toUri().buildUpon()?.scheme("https")?.build()
            Glide.with(imageView.context)
                .load(imgUri)
                .into(imageView)
        }
    }



}