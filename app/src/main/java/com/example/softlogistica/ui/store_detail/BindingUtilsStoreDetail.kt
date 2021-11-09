package com.example.softlogistica.ui.store_detail

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.softlogistica.model.product.Product


const val KW = "KW"
const val CV = "CV"
const val NM = "Nm"
const val HERZ = "Hz"
const val V = "V"
const val AMP = "amp"
const val MILLIMETERS = "mm"
const val KILOGRAMS = "kg"
const val EURO = "€"




@BindingAdapter("bindPrice")
fun  bindPrice(textView: TextView, price: Double?){
    if (price != null){
        textView.text = "$price $EURO"
    }else{
        textView.visibility = View.GONE
    }

}




@BindingAdapter("bindPower")
fun bindPower(textView: TextView, product: Product?){

    if (product?.power != null){
        if (product.familyID == 1){
            textView.text = product.power.toString() + KW
        }else if(product.familyID == 2){
            textView.text = product.power.toString() + CV
        }
    }else{
        textView.visibility = View.GONE
    }
}

@BindingAdapter("bindPar")
fun bindPar(textView: TextView, product: Product?){

    if (product?.par != null){
        textView.text = product.par.toString() + NM
    }else{
        textView.visibility = View.GONE
    }
}



@BindingAdapter("bindFrequenzy")
fun bindFrequenzy(textView: TextView, product: Product?){

    if (product?.frequenzy_hz != null){
        textView.text = product.frequenzy_hz.toString() + HERZ
    }else{
        textView.visibility = View.GONE
    }
}

@BindingAdapter("bindVolt")
fun bindVolt(textView: TextView, product: Product?){

    if (product?.voltage != null){
        textView.text = product.voltage.toString() + V
    }else{
        textView.visibility = View.GONE
    }
}


@BindingAdapter("bindAmps")
fun bindAmps(textView: TextView, intensity: Double?){

    if (intensity != null){
        textView.text = intensity.toString() + AMP
    }else{
        textView.visibility = View.GONE
    }
}


@BindingAdapter("bindMilimeters")
fun bindMilimeters(textView: TextView, value: Int?){

    if (value != null){
        textView.text = value.toString() + MILLIMETERS
    }else{
        textView.visibility = View.GONE
    }
}

@BindingAdapter("bindKillograms")
fun bindKillograms(textView: TextView, value: Int?){

    if (value != null && value != 0){
        textView.text = value.toString() + KILOGRAMS
    }else{
        textView.visibility = View.GONE
    }
}