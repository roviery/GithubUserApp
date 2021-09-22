package dicoding.roviery.githubuser.views.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dicoding.roviery.githubuser.data.local.FavoriteUser
import dicoding.roviery.githubuser.data.local.FavoriteUserDao
import dicoding.roviery.githubuser.data.local.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDb: UserDatabase?
    private var userDao: FavoriteUserDao?

    init{
        userDb = UserDatabase.getDatabase(application)
        userDao = userDb?.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}