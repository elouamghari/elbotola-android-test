package com.elouamghari.test.elbotola.ui.calendar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.elouamghari.test.elbotola.R
import com.elouamghari.test.elbotola.api.models.Match
import com.elouamghari.test.elbotola.applications.MainApplication
import com.elouamghari.test.elbotola.constants.Constants
import com.elouamghari.test.elbotola.databinding.ItemCalendarCompetitionBinding
import com.elouamghari.test.elbotola.databinding.ItemCalendarMatchBinding
import com.elouamghari.test.elbotola.extensions.setImageLink
import com.elouamghari.test.elbotola.listeners.ListAdapterItemClick
import com.elouamghari.test.elbotola.ui.calendar.models.CalendarItem
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private val listener: ListAdapterItemClick<Match>? = null
) : ListAdapter<CalendarItem, RecyclerView.ViewHolder>(CalendarItemComparator()) {

    private val COMPETITION_VIEW_TYPE = 1010
    private val MATCH_VIEW_TYPE = 2020

    class CalendarItemComparator : DiffUtil.ItemCallback<CalendarItem>(){
        override fun areItemsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
            return oldItem::class == newItem::class
        }

        override fun areContentsTheSame(oldItem: CalendarItem, newItem: CalendarItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (getItem(position) is CalendarItem.CalendarMatch){
            return MATCH_VIEW_TYPE
        }
        return COMPETITION_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == MATCH_VIEW_TYPE){
            return MatchViewHolder(inflater.inflate(R.layout.item_calendar_match, parent, false))
        }
        return CompetitionViewHolder(inflater.inflate(R.layout.item_calendar_competition, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MATCH_VIEW_TYPE){
            (holder as MatchViewHolder).bind(getItem(position) as CalendarItem.CalendarMatch)
        }
        else{
            (holder as CompetitionViewHolder).bind(getItem(position) as CalendarItem.CalendarCompetition)
        }
    }

    inner class CompetitionViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){
        private val binding: ItemCalendarCompetitionBinding = ItemCalendarCompetitionBinding.bind(itemView)
        fun bind(item: CalendarItem.CalendarCompetition){
            binding.titleTextView.text = item.competition.name
            binding.iconImageView.setImageLink(item.competition.getImage())
        }
    }

    inner class MatchViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView){

        private val binding: ItemCalendarMatchBinding = ItemCalendarMatchBinding.bind(itemView)

        init {
            binding.root.setOnClickListener {
                listener?.onItemClick((getItem(layoutPosition) as CalendarItem.CalendarMatch).match)
            }
        }

        fun bind(item: CalendarItem.CalendarMatch){
            setVisibility(item.match.matchDetails.matchStatus)

            when (item.match.matchDetails.winner) {
                "home" -> {
                    binding.homeScoreTextView.setTextColor(getColor(R.color.green))
                    binding.awayScoreTextView.setTextColor(getColor(R.color.black))
                }
                "away" -> {
                    binding.homeScoreTextView.setTextColor(getColor(R.color.black))
                    binding.awayScoreTextView.setTextColor(getColor(R.color.green))
                }
                else -> {
                    binding.homeScoreTextView.setTextColor(getColor(R.color.black))
                    binding.awayScoreTextView.setTextColor(getColor(R.color.black))
                }
            }

            binding.homeNameTextView.text = item.match.contestant[0].name
            binding.homeImageView.setImageLink(item.match.contestant[0].getImage())

            binding.awayNameTextView.text = item.match.contestant[1].name
            binding.awayImageView.setImageLink(item.match.contestant[1].getImage())

            item.match.matchDetails.scores?.let { scores ->
                binding.homeScoreTextView.text = scores.total?.home.toString()
                binding.awayScoreTextView.text = scores.total?.away.toString()
            }

            item.match.matchDetails.matchTime?.let{matchTime ->
                binding.currentTimeTextView.text = "'$matchTime"
            }

            binding.timeTextView.text = formatTime(item.match.time)
        }

        private fun setVisibility(matchStatus: String){
            binding.currentTimeCardView.visibility = if(matchStatus == "Playing") View.VISIBLE else View.INVISIBLE
            binding.timeTextView.visibility = if(matchStatus == "Fixture") View.VISIBLE else View.INVISIBLE
            binding.homeScoreTextView.visibility = if(matchStatus != "Fixture") View.VISIBLE else View.INVISIBLE
            binding.awayScoreTextView.visibility = if(matchStatus != "Fixture") View.VISIBLE else View.INVISIBLE
        }

        private fun formatTime(time: String): String {
            return try{
                var spf = SimpleDateFormat(Constants.TIME_FORMAT, Locale.getDefault())
                val date = spf.parse(time)
                spf = SimpleDateFormat("HH:mm", Locale.getDefault())
                spf.format(date!!)
            } catch (e: Exception){
                time
            }
        }

        @ColorInt private fun getColor(@ColorRes resId: Int): Int{
            return ResourcesCompat.getColor(MainApplication.context.resources, resId, MainApplication.context.theme)
        }
    }
}