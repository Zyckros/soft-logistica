 package com.example.softlogistica.ui.new_product

 import android.view.View
 import android.widget.EditText
 import androidx.databinding.BindingAdapter
 import java.text.DecimalFormat


 @BindingAdapter("bindDouble")
fun  bindDouble(editText: EditText, double: Double?){
        if(double != null){
            val decimalFormat = DecimalFormat("#.##")
            editText.setText(decimalFormat.format(double))
        }else{
            editText.visibility = View.GONE
        }


}
