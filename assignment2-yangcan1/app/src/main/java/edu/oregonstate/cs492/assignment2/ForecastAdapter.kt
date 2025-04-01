package edu.oregonstate.cs492.assignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.oregonstate.cs492.assignment2.data.ForecastPeriod
import edu.oregonstate.cs492.assignment2.data.Main
import edu.oregonstate.cs492.assignment2.data.Weather
import java.util.Calendar
import java.util.Locale

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    private var forecastPeriods: List<ForecastPeriod> = listOf()

    fun updateForecastList(newRepoList: List<ForecastPeriod>?) {
        notifyItemRangeRemoved(0, forecastPeriods.size)
        forecastPeriods = newRepoList ?: listOf()
        notifyItemRangeInserted(0, forecastPeriods.size)
    }

    override fun getItemCount() = forecastPeriods.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(forecastPeriods[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val monthTV: TextView = view.findViewById(R.id.tv_month)
        private val dayTV: TextView = view.findViewById(R.id.tv_day)
        private val highTempTV: TextView = view.findViewById(R.id.tv_high_temp)
        private val lowTempTV: TextView = view.findViewById(R.id.tv_low_temp)
        private val shortDescTV: TextView = view.findViewById(R.id.tv_short_description)
        private val popTV: TextView = view.findViewById(R.id.tv_pop)
        private lateinit var currentForecastPeriod: ForecastPeriod

        init {
            view.setOnLongClickListener {
                Snackbar.make(
                    view,
                    currentForecastPeriod.weather.firstOrNull()?.longDesc ?: "No description",
                    Snackbar.LENGTH_LONG
                ).show()
                true
            }
        }

        fun bind(forecastPeriod: ForecastPeriod) {
            currentForecastPeriod = forecastPeriod

            val datetime = forecastPeriod.dateTime.toString()
            val separated = datetime.split(" ")
            val date = separated[0]
            val dateSeparated = date.split("-")
            val year = dateSeparated[0]
            val month = dateSeparated[1]
            val day = dateSeparated[2]

            //    String[] separated = dateTime.split(" ");
            //    String currentString = "Fruit: they taste good";
            //    String[] separated = currentString.split(":");
            //    separated[0]; // this will contain "Fruit"
            //    separated[1]; // this will contain " they taste good"

            val cal = Calendar.getInstance()
            cal.set(year.toInt(), month.toInt(), day.toInt())

            monthTV.text = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())
            dayTV.text = cal.get(Calendar.DAY_OF_MONTH).toString()
            highTempTV.text = forecastPeriod.main.highTemp.toString() + "°F"
            lowTempTV.text = forecastPeriod.main.lowTemp.toString() + "°F"
            popTV.text = (forecastPeriod.pop * 100.0).toInt().toString() + "% precip."
            shortDescTV.text = forecastPeriod.weather[0].shortDesc
        }
    }
}