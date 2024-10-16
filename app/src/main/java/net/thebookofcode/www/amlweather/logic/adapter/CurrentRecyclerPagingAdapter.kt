package net.thebookofcode.www.amlweather.logic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import net.thebookofcode.www.amlweather.data.local.room.entities.HourCache
import net.thebookofcode.www.amlweather.databinding.CurrentListLayoutBinding
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.farenheitToDegree
import net.thebookofcode.www.amlweather.ui.util.Utilities.Companion.getIcon

class CurrentRecyclerPagingAdapter: PagingDataAdapter<HourCache, CurrentRecyclerPagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    inner class ViewHolder(private val itemBinding: CurrentListLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(hour: HourCache) = with(itemBinding) {
            icon.setImageResource(getIcon(hour.icon))
            temp.text = farenheitToDegree(hour.temp)
            time.text = hour.time
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HourCache>() {
            override fun areItemsTheSame(oldItem: HourCache, newItem: HourCache): Boolean {
                return oldItem.time == newItem.time
            }

            override fun areContentsTheSame(oldItem: HourCache, newItem: HourCache): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHour = getItem(position)
        currentHour?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CurrentListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

}