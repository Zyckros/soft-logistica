package com.example.softlogistica.login
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.softlogistica.BaseActivity.Companion.showToast
import com.example.softlogistica.MenuActivity
import com.example.softlogistica.R
import com.example.softlogistica.SplashActivity
import com.example.softlogistica.databinding.ActivityLoginBinding
import com.example.softlogistica.session.AccessData
import org.jetbrains.anko.intentFor
import retrofit2.Response


class LoginActivity : AppCompatActivity(), ResultLogin {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel : LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPref = applicationContext.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        AccessData.setPreferences(sharedPref)

        if(AccessData.isUserLogIn()){
            startActivity(intentFor<SplashActivity>())
        }else {

            binding = ActivityLoginBinding.inflate(layoutInflater)

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            val application = requireNotNull(this).application
            val loginViewModelFactory = LoginViewModelFactory(application)

            loginViewModel = ViewModelProvider(this, loginViewModelFactory).get(LoginViewModel::class.java)

            binding.viewModelLogin = loginViewModel
            binding.progressBar.visibility = View.GONE

            observers()

            binding.lifecycleOwner = this

            setContentView(binding.root)
        }
    }


    private fun observers() {
        loginViewModel.failLogin.observe(this, Observer {
            if (it != null) {
                it.let {
                    binding.progressBar.visibility = View.GONE
                    val toast = Toast.makeText(this, R.string.error_login, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 250)
                    toast.show()
                    loginViewModel.onLoginFailed()
                }
            }
        })

        loginViewModel.fetchLogin.observe(this, Observer {
            if (it != null) {
                it.let {
                    binding.progressBar.visibility = View.VISIBLE
                    loginViewModel.onLoginFetched()
                }
            }
        })

        loginViewModel.userLogin.observe(this, Observer {
            binding.progressBar.visibility = View.GONE
            if (it != null) {
                it.let {
                    startActivity(intentFor<SplashActivity>())
                    loginViewModel.onMenuActivityNavigated()
                }
            }
        })
    }


    override fun failure(t: String) {
        super.failure(t)
        binding.progressBar.visibility = View.GONE
        showToast(this,"Fallo en el inicio de sesión, intentelo de nuevo", Toast.LENGTH_SHORT)
    }

    override fun success(response: Response<String>) {
        super.success(response)
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this,"Login correcto!", Toast.LENGTH_SHORT).show()
        startActivity(intentFor<MenuActivity>())
    }
}

interface ResultLogin{
    fun success(response: Response<String>){}
    fun failure(t: String){}
}



