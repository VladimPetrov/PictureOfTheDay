package ru.gb.pictureoftheday.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.LocalDate

class MainViewModel(val repository: NasaRepository) : ViewModel() {
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
                _image.emit(repository.pictureOfTheDay())
            } catch (exc: IOException) {
                _error.emit("Network error")
            }
            _loading.emit(false)
        }

    }
    fun requestPictureOfAnotherDay(date:String) {

        viewModelScope.launch {
            _loading.emit(true)
            try {
                _image.emit(repository.pictureOfAnotherDay(date))
            } catch (exc: IOException) {
                _error.emit("Network error")
            }
            _loading.emit(false)
        }

    }
}

class MainViewModelFactory(val repository: NasaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = MainViewModel(repository) as T
}