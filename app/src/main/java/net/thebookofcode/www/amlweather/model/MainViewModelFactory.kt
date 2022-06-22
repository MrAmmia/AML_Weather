package net.thebookofcode.www.amlweather.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import net.thebookofcode.www.amlweather.repository.Repository

class MainViewModelFactory(private val repository: Repository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}