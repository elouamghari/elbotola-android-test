package com.elouamghari.test.elbotola.ui.calendar

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elouamghari.test.elbotola.R
import com.elouamghari.test.elbotola.api.models.Match
import com.elouamghari.test.elbotola.databinding.FragmentCalendarBinding
import com.elouamghari.test.elbotola.extensions.toDisplayableText
import com.elouamghari.test.elbotola.ui.calendar.adapters.CalendarAdapter
import com.elouamghari.test.elbotola.ui.calendar.models.CalendarItem
import com.elouamghari.test.elbotola.ui.match.MatchDialogFragment
import com.elouamghari.test.elbotola.viewmodels.MainViewModel
import com.elouamghari.test.elbotola.utils.MenuSearchViewUtils

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding
    private val viewModel: MainViewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    private val adapter = CalendarAdapter(this::onMatchClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater)
        binding.matchesRecyclerView.adapter = adapter
        binding.progressBar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.calendar.observe(viewLifecycleOwner, this::onMatchesChanged)
        viewModel.dateFilter.observe(viewLifecycleOwner){ date ->
            binding.dateTextView.text = date.toDisplayableText()
        }
        binding.liveMatchesButton.setOnClickListener { viewModel.live() }
        binding.nextButton.setOnClickListener { viewModel.nextDay() }
        binding.previousButton.setOnClickListener { viewModel.previousDay() }
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
                    viewModel.searchCalendar(newText ?: "")
                    return true
                }
            })
    }

    private fun onMatchesChanged(matches: List<CalendarItem>){
        binding.progressBar.visibility = View.GONE
        if (matches.isEmpty()){
            binding.emptyList.visibility = View.VISIBLE
            binding.matchesRecyclerView.visibility = View.GONE
        }
        else{
            binding.matchesRecyclerView.visibility = View.VISIBLE
            binding.emptyList.visibility = View.GONE
        }
        adapter.submitList(matches)
    }

    private fun onMatchClick(match: Match){
        MatchDialogFragment().show(requireActivity().supportFragmentManager, match)
    }
}