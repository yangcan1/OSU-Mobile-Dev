package edu.oregonstate.cs492.assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import edu.oregonstate.cs492.assignment2.data.ForecastPeriod
import edu.oregonstate.cs492.assignment2.data.ForecastSearchResults
import edu.oregonstate.cs492.assignment2.data.ForecastService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val viewModel: ForecastViewModel by viewModels()

//    private val forecastService = ForecastService.create()
    private val adapter: ForecastAdapter = ForecastAdapter()

    private lateinit var forecastListRV: RecyclerView
    private lateinit var searchErrorTV: TextView
    private lateinit var loadingIndicator: CircularProgressIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchErrorTV = findViewById(R.id.tv_search_error)
        loadingIndicator = findViewById(R.id.loading_indicator)

        forecastListRV = findViewById<RecyclerView>(R.id.rv_forecast_list)
        forecastListRV.layoutManager = LinearLayoutManager(this)
        forecastListRV.setHasFixedSize(true)

        forecastListRV.adapter = adapter

        viewModel.searchResults.observe(this) {
            searchResults -> adapter.updateForecastList(searchResults)

        }

        viewModel.loadingStatus.observe(this) {
                loadingStatus ->
            when (loadingStatus) {
                LoadingStatus.LOADING -> {
                    forecastListRV.visibility = View.INVISIBLE
                    loadingIndicator.visibility = View.VISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
                LoadingStatus.ERROR -> {
                    forecastListRV.visibility = View.INVISIBLE
                    loadingIndicator.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.VISIBLE
                }
                else -> {
                    forecastListRV.visibility = View.VISIBLE
                    loadingIndicator.visibility = View.INVISIBLE
                    searchErrorTV.visibility = View.INVISIBLE
                }
            }
        }

        viewModel.error.observe(this) {
                error -> searchErrorTV.text = getString(
            R.string.search_error,
            error
        )
        }


        viewModel.loadSearchResults("Corvallis,OR,US, metric, 8f828d7dcc772d70acc1e436ada69c5d")
//        doForecastSearch("Corvallis,OR,US", "metric", "8f828d7dcc772d70acc1e436ada69c5d")
    }

//    private fun doForecastSearch(query: String, units: String, appid: String) {
//        loadingIndicator.visibility = View.VISIBLE
//        searchErrorTV.visibility = View.INVISIBLE
//        forecastListRV.visibility = View.INVISIBLE
//        forecastService.searchForecasts(query, units, appid).enqueue(object : Callback<ForecastSearchResults> {
//            override fun onResponse(call: Call<ForecastSearchResults>, response: Response<ForecastSearchResults>) {
//                Log.d("MainActivity", "Status code: ${response.code()}")
//                Log.d("MainActivity", "Response body: ${response.body()}")
//                loadingIndicator.visibility = View.INVISIBLE
//                if (response.isSuccessful) {
//                    forecastListRV.visibility = View.VISIBLE
//                    adapter.updateForecastList(response.body()?.list)
//
//                } else {
//                    searchErrorTV.visibility = View.VISIBLE
//                    searchErrorTV.text = getString(
//                        R.string.search_error,
//                        response.errorBody()?.string() ?: "unknown error")
//                }
//            }
//
//            override fun onFailure(call: Call<ForecastSearchResults>, t: Throwable) {
//                Log.d("MainActivity", "Error making API call: ${t.message}")
//                loadingIndicator.visibility = View.INVISIBLE
//                searchErrorTV.visibility = View.VISIBLE
//                searchErrorTV.text = getString(
//                    R.string.search_error,
//                    t.message)
//            }
//        })
//    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "Running onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MainActivity", "Running onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("MainActivity", "Running onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "Running onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "Running onDestroy()")
    }



}