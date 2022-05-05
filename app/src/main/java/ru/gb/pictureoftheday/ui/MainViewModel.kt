package ru.gb.pictureoftheday.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.gb.pictureoftheday.api.PictureNasaResponse
import ru.gb.pictureoftheday.domain.NasaRepository
import java.io.IOException

class MainViewModel(val repositiry: NasaRepository) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loadind: Flow<Boolean> = _loading
    private val _image: MutableSharedFlow<PictureNasaResponse?> = MutableStateFlow(null)
    val image: Flow<PictureNasaResponse?> = _image
    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error
    fun requestPictureOfTheDay() {

        viewModelScope.launch {
            _loading.emit(true)
            try {
                _image.emit(repositiry.pictureOfTheDay())
            } catch (exc: IOException) {
                _error.emit("Network error")
            }
            _loading.emit(false)
        }

    }
}

class MainViewModelFactory(val repositiry: NasaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repositiry) as T
}