package net.thebookofcode.www.amlweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.thebookofcode.www.amlweather.R
import net.thebookofcode.www.amlweather.databinding.FutureListLayoutBinding
import net.thebookofcode.www.amlweather.entity.Days
import net.thebookofcode.www.amlweather.room.DaysCache
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class FutureRecyclerAdapter(val days:List<DaysCache>): RecyclerView.Adapter<FutureRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: FutureListLayoutBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(day: DaysCache){
            itemBinding.icon.setImageResource(getIcon(day.icon)!!)
            itemBinding.temp.text = farenheitToDegree(day.temp)
            itemBinding.day.text = getFormattedDate(day.date)
            itemBinding.description.text = day.icon
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = FutureListLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentDay = days[position]
        holder.bind(currentDay)
    }

    override fun getItemCount(): Int {
        return days.size
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

    fun getFormattedDate(date:String):String{
        var format = SimpleDateFormat("yyyy-MM-dd")
        val newDate: Date = format.parse(date)

        format = SimpleDateFormat("EEE,dd")
        return format.format(newDate)
    }
}