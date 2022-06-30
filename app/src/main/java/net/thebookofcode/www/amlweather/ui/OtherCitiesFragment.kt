package net.thebookofcode.www.amlweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.thebookofcode.www.amlweather.adapter.OtherCitiesRecyclerAdapter
import net.thebookofcode.www.amlweather.databinding.FragmentOtherCitiesBinding
import net.thebookofcode.www.amlweather.model.MainViewModel
import net.thebookofcode.www.amlweather.model.MainViewModelFactory
import net.thebookofcode.www.amlweather.repository.Repository

class OtherCitiesFragment : Fragment() {
    private var _binding:FragmentOtherCitiesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(Repository())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentOtherCitiesBinding.inflate(inflater, container, false)
        binding.shimmer.startShimmer()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        viewModel.myResponseOtherCitiesWeather.observe(viewLifecycleOwner, Observer{
            binding.shimmer.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
            binding.shimmer.stopShimmer()
            val adapter = OtherCitiesRecyclerAdapter(it)
            binding.recyclerView.adapter = adapter
        })
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

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}