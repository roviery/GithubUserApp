package dicoding.roviery.githubuser.views.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.roviery.githubuser.R
import dicoding.roviery.githubuser.databinding.FragmentFollowersBinding
import dicoding.roviery.githubuser.databinding.FragmentFollowingBinding
import dicoding.roviery.githubuser.views.UserAdapter
import dicoding.roviery.githubuser.views.detail.DetailActivity
import dicoding.roviery.githubuser.views.detail.viewmodel.FollowersViewModel
import dicoding.roviery.githubuser.views.detail.viewmodel.FollowingViewModel


class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowingBinding.inflate(layoutInflater)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply{
            detailRvFollowing.setHasFixedSize(true)
            detailRvFollowing.layoutManager = LinearLayoutManager(activity)
            detailRvFollowing.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        viewModel.setListFollowing(username)
        viewModel.getListFollowing().observe(viewLifecycleOwner, {
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun showLoading(state: Boolean){
        if (state){
            binding.detailProgressBar.visibility = View.VISIBLE
        }
        else{
            binding.detailProgressBar.visibility = View.GONE
        }
    }

}