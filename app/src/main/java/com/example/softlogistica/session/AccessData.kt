package com.example.softlogistica.session


import android.content.SharedPreferences
import comampleftlogisticaaddata.User


const val USER_NAME = "USER_NAME"
const val USER_TOKEN  = "USER_TOKEN"
const val USER_LAST_NAME = "USER_LAST_NAME"
const val USER_CODE = "USER_CODE"
const val USER_EMAIL = "USER_EMAIL"
const val USER_ROLE = "USER_ROLE"

class AccessData {
    companion object {
        var sharedPreferencesManager: SharedPreferences ?= null

        fun clearUser() {

            val edit = sharedPreferencesManager?.edit()
            edit?.putString(USER_NAME, null)?.apply()
            edit?.putString(USER_TOKEN, null)?.apply()
            edit?.putString(USER_LAST_NAME, null)?.apply()
            edit?.putString(USER_CODE, null)?.apply()
            edit?.putString(USER_EMAIL, null)?.apply()
            edit?.putString(USER_ROLE, null)?.apply()
        }

        fun setPreferences(sharePref : SharedPreferences){
            sharedPreferencesManager = sharePref
        }

        fun isUserLogIn(): Boolean {
            return sharedPreferencesManager?.getString(USER_TOKEN, "") != "" && sharedPreferencesManager?.getString(USER_TOKEN, "") != null
        }

        fun isUserAdmin(): Boolean{
            return sharedPreferencesManager?.getString(USER_ROLE, "" ).equals("admin")
        }
        fun isUserClient(): Boolean{
            return sharedPreferencesManager?.getString(USER_ROLE, "" ).equals("client")
        }
        fun isUserEmployee(): Boolean{
            return sharedPreferencesManager?.getString(USER_ROLE, "" ).equals("employee")
        }

        fun setAccessToken(token: String?) {
            token?.let { sharedPreferencesManager?.edit()?.putString(USER_TOKEN, it)?.apply() }
        }

        fun saveLoginInformation( user: User) {
                val edit = sharedPreferencesManager?.edit()
            edit?.putString(USER_NAME, user.name)?.apply()
            edit?.putString(USER_TOKEN, user.token)?.apply()
            edit?.putString(USER_EMAIL, user.email)?.apply()
            edit?.putString(USER_ROLE, user.role)?.apply()
            //sharedPreferencesManager?.edit()?.putString(USER_LAST_NAME, user?.last_name ?: "")?.apply()
            //sharedPreferencesManager?.edit()?.putString(USER_CODE, user?.code ?: "")?.apply()
        }

        fun getUserName(): String? {
            return sharedPreferencesManager?.getString(USER_NAME, "")
        }
        fun getAccessToken(): String{
            return USER_TOKEN
        }

        fun getRole(): String? {
            return sharedPreferencesManager?.getString(USER_ROLE, "")
        }
    }
}