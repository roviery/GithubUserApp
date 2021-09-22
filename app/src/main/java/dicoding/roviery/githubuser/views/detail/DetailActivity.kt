package dicoding.roviery.githubuser.views.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dicoding.roviery.githubuser.R
import dicoding.roviery.githubuser.databinding.ActivityDetailBinding
import dicoding.roviery.githubuser.views.detail.viewmodel.DetailViewModel
import dicoding.roviery.githubuser.views.favorite.FavoriteActivity
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following,
            R.string.followers
        )

        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_btn)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_URL)

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        setViewModel(username)
        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count != null){
                    if (count > 0){
                        binding.favBtn.isChecked = true
                        isChecked = true
                    }
                    else{
                        binding.favBtn.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.favBtn.setOnClickListener{
            isChecked = !isChecked
            if (isChecked){
                viewModel.addToFavorite(username!!, id, avatarUrl!!)
            }else{
                viewModel.removeFromFavorite(id)
            }
            binding.favBtn.isChecked = isChecked
        }


        setViewPager(bundle)
    }


    private fun setViewPager(data: Bundle){
        val sectionsPagerAdapter = SectionsPagerAdapter(this, data)
        val viewPager: ViewPager2 = binding.detailViewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.detailTabs
        TabLayoutMediator(tabs, viewPager){ tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setViewModel(username: String?){
        viewModel = ViewModelProvider(this).get(
            DetailViewModel::class.java)
        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this, {
            CoroutineScope(Dispatchers.Main).launch{
                delay(1000)
                if (it != null){
                    binding.apply{
                        Glide.with(this@DetailActivity)
                            .load(it.avatar_url)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .circleCrop()
                            .into(detailImg)
                        detailName.text = it.name
                        detailUsername.text = it.login
                        detailFollowersNumber.text = it.followers.toString()
                        detailFollowingNumber.text = it.following.toString()
                        if (it.company != null)
                            detailCompanyName.text = it.company
                        else
                            detailCompanyName.text = "-"
                        if (it.location != null)
                            detailLocationName.text = it.location
                        else
                            detailLocationName.text = "-"
                        detailRepoNumber.text = it.public_repos.toString()
                        detailShimmer.visibility = View.GONE
                    }
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        else if (item.itemId == R.id.action_favorite){
            val mIntent = Intent(this, FavoriteActivity::class.java)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}