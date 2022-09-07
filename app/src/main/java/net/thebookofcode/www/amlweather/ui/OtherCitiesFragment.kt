package net.thebookofcode.www.amlweather.ui

import android.os.Bundle
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

class OtherCitiesFragment : Fragment() {
    private var _binding:FragmentOtherCitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()
    val towns = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOtherCitiesBinding.inflate(inflater, container, false)
        binding.shimmer.startShimmer()
        addTowns()
        val adapter = OtherCitiesRecyclerAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        /*for (town: String in towns) {
            viewModel.getWeatherByTown(town)
            adapter.addWeather(viewModel.myResponseByTown.value!!.data!!)
        }*/
        if(adapter.itemCount > 0){
            binding.shimmer.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.shimmer.stopShimmer()
            binding.recyclerView.adapter = adapter
        }

        // also remember to save state of this fragment

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.myResponseByTown.observe(viewLifecycleOwner,Observer{
                    TODO("Bind result to popup")
                })
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

        })

        return binding.root
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