package com.example.countries.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.countries.R
import com.example.countries.viewmodel.ListViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel : ListViewModel
    private val countriesAdapter = CountryListAdapter(arrayListOf()) //passing empty list

    private var countriesList: RecyclerView? = null
    private var tvError : TextView? = null
    private var progressBarLoading : ProgressBar? = null
    private var swipeRefreshLayout : SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countriesList = findViewById(R.id.countriesList);
        tvError = findViewById(R.id.list_error)
        progressBarLoading = findViewById(R.id.loading_view)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        //this is how to initialise view model
        viewModel = ViewModelProviders.of(this)[ListViewModel::class.java]
        viewModel.refresh()

        countriesList?.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = countriesAdapter
        }

        //for swipe refresh
        swipeRefreshLayout?.setOnRefreshListener {
            swipeRefreshLayout?.isRefreshing = false
            viewModel.refresh()
        }


        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer { countries ->
            countries?.let {
                countriesList?.visibility = View.VISIBLE
                 countriesAdapter.updateCountries(it)
            }
        })

        viewModel.countryLoadError.observe(this, Observer { isError ->
            isError?.let{
                tvError?.visibility = if(it) View.VISIBLE else View.GONE
            }
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let { progressBarLoading?.visibility = if(it) View.VISIBLE else View.GONE
            if(it){
                tvError?.visibility = View.GONE
                countriesList?.visibility = View.GONE
            }
            }
        })
    }
}