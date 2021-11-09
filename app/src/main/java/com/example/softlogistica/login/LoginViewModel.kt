package com.example.softlogistica.login
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.softlogistica.session.AccessData
import comampleftlogisticaaddata.LoadData
import comampleftlogisticaaddata.User


class LoginViewModel(application: Application) : AndroidViewModel(application) {

            val email = MutableLiveData<String>()
            val password = MutableLiveData<String>()
            val rememberMe = MutableLiveData<Boolean>()

            private val _failLogin = MutableLiveData<Boolean>()
            val failLogin : LiveData<Boolean> = _failLogin

            private val _userLogin = MutableLiveData<Boolean>()
            val  userLogin : LiveData<Boolean> = _userLogin

            private val _fetchLogin = MutableLiveData<Boolean>()
            val  fetchLogin : LiveData<Boolean> = _fetchLogin

        init {
            rememberMe.value = false
        }

   fun login() {
       _fetchLogin.value = true
       var user : User? = null

       when(email.value){
           "admin" -> user = LoadData.admin
           "employee" -> user = LoadData.employee
           "client" -> user = LoadData.client
       }

       if(user != null){
           savePrefs(user)
           _userLogin.value = true
       }else{
            _failLogin.value = true
       }


       /* Service.retrofitService.getAuth().enqueue(
            object: Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    view.failure(t.toString())
                }
                override fun onResponse(call: Call<String>,
                response: Response<String>) {
                    //TODO coger el response.body y pasarlo al AccessData para la autenticacion del usuario
                    if(response.code() != 200){
                        view.failure(response.body().toString())
                    }else{
                        view.success(response)
                    }
                }
            })*/
    }

    private fun savePrefs(user: User) {
        AccessData.saveLoginInformation(user)

    }

    fun onMenuActivityNavigated() {
        _userLogin.value = null
    }

    fun onLoginFailed() {
        _failLogin.value = null
    }

    fun onLoginFetched(){
        _fetchLogin.value = null
    }

}