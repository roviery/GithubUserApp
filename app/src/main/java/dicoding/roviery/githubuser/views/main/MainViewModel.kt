package dicoding.roviery.githubuser.views.main

import android.util.Log
import androidx.lifecycle.*
import dicoding.roviery.githubuser.api.RetrofitClient
import dicoding.roviery.githubuser.data.User
import dicoding.roviery.githubuser.data.UserResponse
import dicoding.roviery.githubuser.preferences.SettingsPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingsPreferences): ViewModel() {
    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUser(query: String){
        RetrofitClient.apiInstance
            .getSearchUsers(query)
            .enqueue(object: Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("On Failure: ", t.message!!)
                }
            })
    }

    fun getSearchUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}