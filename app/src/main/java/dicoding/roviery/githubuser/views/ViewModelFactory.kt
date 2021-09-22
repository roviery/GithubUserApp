package dicoding.roviery.githubuser.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dicoding.roviery.githubuser.preferences.SettingsPreferences
import dicoding.roviery.githubuser.views.main.MainViewModel

class ViewModelFactory(private val pref: SettingsPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}