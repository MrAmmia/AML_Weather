package net.thebookofcode.www.amlweather.logic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.thebookofcode.www.amlweather.R
import net.thebookofcode.www.amlweather.databinding.CurrentListLayoutBinding
import net.thebookofcode.www.amlweather.data.local.room.entities.HourCache
import kotlin.collections.HashMap

class CurrentRecyclerAdapter(var hours: List<HourCache>) :
    RecyclerView.Adapter<CurrentRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: CurrentListLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(hour: HourCache) = with(itemBinding) {
            icon.setImageResource(getIcon(hour.icon)!!)
            temp.text = farenheitToDegree(hour.temp)
            time.text = hour.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            CurrentListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentHour = hours[position]
        holder.bind(currentHour)
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    fun farenheitToDegree(temp: Double): String {
        val doubleTemp = ((temp - 32) * 5) / 9
        return doubleTemp.toInt().toString() + "\u00b0"
    }

    fun getIcon(icon: String): Int? {
        val iconMap = HashMap<String, Int>()
        iconMap["snow"] = R.drawable.snow
        iconMap["rain"] = R.drawable.rain
        //iconMap["fog"] = R.drawable.fog
        iconMap["wind"] = R.drawable.wind
        iconMap["cloudy"] = R.drawable.cloudy
        iconMap["partly-cloudy-day"] = R.drawable.partly_covered_day
        iconMap["partly-cloudy-night"] = R.drawable.partly_covered_night
        iconMap["clear-day"] = R.drawable.clear_day
        iconMap["clear-night"] = R.drawable.clear_night
        iconMap["snow-showers-day"] = R.drawable.snow_showers_day
        iconMap["snow-showers-night"] = R.drawable.snow_showers_night
        iconMap["thunder-rain"] = R.drawable.thunder_shower_day
        iconMap["thunder-showers-day"] = R.drawable.thunder_shower_day
        iconMap["thunder-showers-night"] = R.drawable.thunder_shower_night
        iconMap["showers-day"] = R.drawable.showers_day
        iconMap["showers-night"] = R.drawable.showers_night
        return iconMap[icon]
    }

    fun setData(data: List<HourCache>) {
        hours = data
    }
}