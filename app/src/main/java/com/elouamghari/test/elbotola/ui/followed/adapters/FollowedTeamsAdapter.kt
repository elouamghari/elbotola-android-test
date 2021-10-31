package com.elouamghari.test.elbotola.ui.followed.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elouamghari.test.elbotola.R
import com.elouamghari.test.elbotola.api.models.Contestant
import com.elouamghari.test.elbotola.databinding.ItemFollowedTeamBinding
import com.elouamghari.test.elbotola.extensions.setImageLink
import com.elouamghari.test.elbotola.listeners.ListAdapterItemClick

class FollowedTeamsAdapter(
    private val listener: ListAdapterItemClick<Contestant>? = null
) : ListAdapter<Contestant, FollowedTeamsAdapter.TeamHolder>(TeamComparator()) {

    class TeamComparator : DiffUtil.ItemCallback<Contestant>(){
        override fun areItemsTheSame(oldItem: Contestant, newItem: Contestant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contestant, newItem: Contestant): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamHolder {
        return TeamHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_followed_team, parent, false))
    }

    override fun onBindViewHolder(holder: TeamHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeamHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = ItemFollowedTeamBinding.bind(itemView)

        init {
            binding.root.setOnClickListener { listener?.onItemClick(getItem(layoutPosition)) }
        }

        fun bind(contestant: Contestant){
            binding.teamNameTextView.text = contestant.name
            binding.teamImageView.setImageLink(contestant.getImage())
        }
    }
}