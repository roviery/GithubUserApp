package dicoding.roviery.githubuser.views.detail.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dicoding.roviery.githubuser.databinding.FragmentFollowersBinding
import dicoding.roviery.githubuser.views.UserAdapter
import dicoding.roviery.githubuser.views.detail.DetailActivity
import dicoding.roviery.githubuser.views.detail.viewmodel.FollowersViewModel


class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args = arguments
        username = args?.getString(DetailActivity.EXTRA_USERNAME).toString()

        _binding = FragmentFollowersBinding.inflate(layoutInflater)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply{
            detailRvFollowers.setHasFixedSize(true)
            detailRvFollowers.layoutManager = LinearLayoutManager(activity)
            detailRvFollowers.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        viewModel.setListFollowers(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner, {
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