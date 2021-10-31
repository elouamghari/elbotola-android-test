package com.elouamghari.test.elbotola.ui.followed

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elouamghari.test.elbotola.R
import com.elouamghari.test.elbotola.api.models.Contestant
import com.elouamghari.test.elbotola.databinding.FragmentFollowedBinding
import com.elouamghari.test.elbotola.managers.FollowedTeamsManager
import com.elouamghari.test.elbotola.ui.followed.adapters.FollowedTeamsAdapter
import com.elouamghari.test.elbotola.viewmodels.MainViewModel
import com.elouamghari.test.elbotola.utils.MenuSearchViewUtils

class FollowedFragment : Fragment() {

    private lateinit var binding: FragmentFollowedBinding

    private val adapter = FollowedTeamsAdapter(this::onTeamClicked)

    private val viewModel: MainViewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowedBinding.inflate(inflater)
        binding.followedRecyclerView.adapter = adapter
        binding.progressBar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_options_menu, menu)
        MenuSearchViewUtils.setup(requireActivity(), menu, R.id.optionSearch,
            object :  SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchFavoriteTeams(newText ?: "")
                    return true
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.followed.observe(viewLifecycleOwner, this::onFollowedTeamsChanged)
    }

    private fun onFollowedTeamsChanged(teams: List<Contestant>){
        binding.progressBar.visibility = View.GONE
        if (teams.isEmpty()){
            binding.emptyList.visibility = View.VISIBLE
            binding.followedRecyclerView.visibility = View.GONE
        }
        else{
            binding.followedRecyclerView.visibility = View.VISIBLE
            binding.emptyList.visibility = View.GONE
        }
        adapter.submitList(teams)
    }

    private fun onTeamClicked(team: Contestant){
        viewModel.unfollow(team)
    }
}