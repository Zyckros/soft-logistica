package com.example.softlogistica

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.softlogistica.databinding.ActivityMenuBinding
import com.example.softlogistica.login.LoginActivity
import com.example.softlogistica.session.AccessData
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.jetbrains.anko.intentFor


class MenuActivity : BaseActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMenu.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val bottomNav = binding.bottomNavigationView

        customNavView()

        val navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
               R.id.nav_store, R.id.nav_services, R.id.nav_product, R.id.nav_budget, R.id.nav_order, R.id.nav_delivery_note, R.id.nav_invoice , R.id.nav_qr_code, R.id.nav_document_signing , R.id.bottom_nav_home, R.id.bottom_nav_chat, R.id.bottom_nav_cart, R.id.bottom_nav_notification
            ), drawerLayout
        )


        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        bottomNav.bottomNavigation.setupWithNavController(navController)


    }


    private fun customNavView() {
        if(AccessData.isUserAdmin()){
            binding.navView.menu.findItem(R.id.nav_product).isVisible = true
            binding.navView.menu.findItem(R.id.nav_qr_code).isVisible = true
        }else if(AccessData.isUserEmployee()){
            binding.navView.menu.findItem(R.id.nav_product).isVisible = true
            binding.navView.menu.findItem(R.id.nav_qr_code).isVisible = true
        }else if(AccessData.isUserClient()){

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_logout ){

            MaterialAlertDialogBuilder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Estas seguro que deseas cerrar la sesión?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    AccessData.clearUser()
                    startActivity(intentFor<LoginActivity>())
                    Toast.makeText(baseContext,"Se ha eliminado el usuario y se ha cerrado sesión", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton("No", null).show()

        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}