package net.thebookofcode.www.amlweather.ui.util

import android.app.AlertDialog
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import net.thebookofcode.www.amlweather.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.HashMap
import java.util.Locale

class Utilities {
    companion object {
        fun showInContextUI(context: Context) {
            AlertDialog.Builder(context).setTitle("Alert")
                .setMessage("AML Weather requires your location in order to get weather")
                .setCancelable(true)
                .show()
        }

        fun checkLocation(context: Context) {
            AlertDialog.Builder(context).setTitle("Alert")
                .setMessage("AML Weather could not get location, please check Location is turned on and try again")
                .setCancelable(true)
                .show()
        }

        fun errorOccurred(context: Context, message: String) {
            AlertDialog.Builder(context).setTitle("Alert")
                .setMessage("Oops, an error occurred\n${message}")
                .setCancelable(true)
                .show()
        }

        fun farenheitToDegree(temp: Double): String {
            val doubleTemp = ((temp - 32) * 5) / 9
            return doubleTemp.toInt().toString() + "\u00b0"
        }

        fun getFormattedDate(): String {
            val formattedDate = SimpleDateFormat("dd,MMMM", Locale.getDefault()).format(Date())
            return "Today $formattedDate"
        }

        fun getFormattedDate(date: String): String {
            var format = SimpleDateFormat("yyyy-MM-dd")
            val newDate: Date = format.parse(date)

            format = SimpleDateFormat("EEEE, dd MMM")
            return format.format(newDate)
        }

        fun getIcon(icon: String): Int? {
            val iconMap = HashMap<String, Int>()
            iconMap["snow"] = R.drawable.snow
            iconMap["rain"] = R.drawable.rain
            iconMap["fog"] = R.drawable.fog
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

        fun formatPercent(item: Double): String {
            return item.toInt().toString() + "%"
        }

        fun formatKilometers(item: Double): String {
            return item.toInt().toString() + "Km/h"
        }

        fun betterResolvedAddress(context: Context?, location: Location): String {
            // work on this, Should be able to get it from location parameters tho
            val geocoder = context?.let { Geocoder(it, Locale.getDefault()) }
            val addresses: List<Address> =
                geocoder?.getFromLocation(location.latitude, location.longitude, 1)!!
            val cityName: String = addresses[0].getAddressLine(0)
            val parts = cityName.split(",")
            return parts[parts.size - 3] + ", " + parts[parts.size - 1]
        }
    }
}