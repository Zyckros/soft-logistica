package com.example.softlogistica

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_AUTO
import com.google.android.material.bottomnavigation.LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED


open class BaseActivity : AppCompatActivity(){

    /**
     * zyckros@gmail.com
     **/
    companion object{
        //TODO Crear funciones para utilizarlas en los diferentes fragmentos, como mostrar un Toas o un Dialog

        fun showToast(context : Context, message : String?, length : Int){
                Toast.makeText(context, message, length).show()
        }
    }

    private lateinit var  bottomNav : BottomNavigationView

    fun enableBottomNAv(){
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.menu.setGroupCheckable(0,true,true)
        bottomNav.labelVisibilityMode = LABEL_VISIBILITY_AUTO
    }

    fun disableBottomNav(){
        bottomNav = findViewById(R.id.bottom_navigation)
        bottomNav.menu.setGroupCheckable(0,false,false)
        bottomNav.menu.getItem(0).isChecked = false
        bottomNav.menu.getItem(1).isChecked = false
        bottomNav.menu.getItem(2).isChecked = false
        bottomNav.menu.getItem(3).isChecked = false
        bottomNav.labelVisibilityMode = LABEL_VISIBILITY_UNLABELED
    }

}