package com.elouamghari.test.elbotola.ui.match

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.elouamghari.test.elbotola.R
import com.elouamghari.test.elbotola.api.models.Contestant
import com.elouamghari.test.elbotola.api.models.Match
import com.elouamghari.test.elbotola.databinding.DialogFragmentMatchBinding
import com.elouamghari.test.elbotola.extensions.follow
import com.elouamghari.test.elbotola.extensions.isFollowed
import com.elouamghari.test.elbotola.extensions.setImageLink
import com.elouamghari.test.elbotola.extensions.unfollow

class MatchDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentMatchBinding
    private var match: Match? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentMatchBinding.inflate(LayoutInflater.from(requireContext()))
        match?.let { onMatchChanged(it) }
        onDialogViewCreated()
        return AlertDialog.Builder(requireContext()).setView(binding.root).create()
    }

    private fun onMatchChanged(match: Match){
        val team1 = match.contestant[0]
        val team2 = match.contestant[1]

        binding.team1ImageView.setImageLink(team1.getImage())
        binding.team2ImageView.setImageLink(team2.getImage())

        binding.team1NameTextView.text = team1.name
        binding.team2NameTextView.text = team2.name

        binding.team1FollowedImageView.setImageResource(getFollowedIcon(team1))
        binding.team2FollowedImageView.setImageResource(getFollowedIcon(team2))

    }

    private fun getFollowedIcon(team: Contestant): Int{
        return if(team.isFollowed()){
            R.drawable.ic_star_blue
        }else{
            R.drawable.ic_star_border
        }
    }

    private fun onDialogViewCreated(){
        binding.team1FollowedImageView.setOnClickListener {
            changeTeamFollowState(0)
        }

        binding.team2FollowedImageView.setOnClickListener {
            changeTeamFollowState(1)
        }
    }

    private fun changeTeamFollowState(teamIndex: Int){
        match?.let {
            val team = it.contestant[teamIndex]
            if (team.isFollowed()){
                team.unfollow()
            }
            else{
                team.follow()
            }
            refreshFollowUI(teamIndex, team)
        }
    }

    private fun refreshFollowUI(teamIndex: Int, team: Contestant) {
        when(teamIndex){
            0 -> binding.team1FollowedImageView.setImageResource(getFollowedIcon(team))
            1 -> binding.team2FollowedImageView.setImageResource(getFollowedIcon(team))
        }
    }

    fun show(manager: FragmentManager, match: Match){
        this.match = match
        show(manager, tag)
    }
}