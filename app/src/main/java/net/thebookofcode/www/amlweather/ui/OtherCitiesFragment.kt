package net.thebookofcode.www.amlweather.ui

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import net.thebookofcode.www.amlweather.adapter.OtherCitiesRecyclerAdapter
import net.thebookofcode.www.amlweather.databinding.FragmentOtherCitiesBinding
import net.thebookofcode.www.amlweather.entity.OtherWeather
import net.thebookofcode.www.amlweather.entity.Weather
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.recyclerIterface.ListItemListener
import net.thebookofcode.www.amlweather.util.DataState

class OtherCitiesFragment : Fragment() {
    private var _binding:FragmentOtherCitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    lateinit var adapter: OtherCitiesRecyclerAdapter
    val towns = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOtherCitiesBinding.inflate(inflater, container, false)
        binding.shimmer.startShimmer()
        addTowns()
        adapter = OtherCitiesRecyclerAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter
        viewModel.getOthers(towns)
        viewModel.other.observe(viewLifecycleOwner, Observer {
            when(it){
                is DataState.Success -> {
                    adapter.setWeather(it.data!!)
                    val size = adapter.itemCount
                    Log.d("Size",size.toString())

                    listLoaded()
                }
                is DataState.Loading -> {
                    listLoading()
                    binding.shimmer.startShimmer()
                }
                is DataState.Error -> {
                    listLoading()
                    errorOccurred(it.error!!)
                }
            }

        })

        // also remember to save state of this fragment

        /*binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }
        })

        adapter.setOnItemClick(object:ListItemListener{
            override fun onItemClick(weather: OtherWeather?) {
                TODO("Not yet implemented")
            }

        })*/

        return binding.root
    }

    private fun listLoaded() {
        binding.shimmer.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
        binding.shimmer.stopShimmer()
    }

    private fun listLoading() {
        binding.shimmer.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
        binding.shimmer.startShimmer()
    }

    private fun errorOccurred(e:Throwable) {
        AlertDialog.Builder(requireContext()).setTitle("Alert")
            .setMessage("Oops, an error occurred\n${e.message}")
            .setCancelable(true)
            .show()
    }

    private fun addTowns() {
        towns.add("London,Uk")
        towns.add("Berlin,Germany")
        towns.add("Madrid,Spain")
        towns.add("Cairo,Egypt")
        towns.add("Dhaka,Bangladesh")
        towns.add("Bogotta,Colombia")
        towns.add("Paris,France")
        towns.add("Johannesburg,South Africa")
        towns.add("Lagos,Nigeria")
        towns.add("Lisbon,Portugal")
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}