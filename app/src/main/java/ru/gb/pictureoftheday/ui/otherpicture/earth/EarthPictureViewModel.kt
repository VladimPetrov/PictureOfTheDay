package ru.gb.pictureoftheday.ui.otherpicture.earth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.gb.pictureoftheday.api.EarthNasaResponse
import ru.gb.pictureoftheday.api.MarsRoverResponse
import ru.gb.pictureoftheday.domain.NasaRepository
import java.io.IOException

class EarthPictureViewModel(val repository: NasaRepository) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loadind: Flow<Boolean> = _loading
    private val _imageList: MutableSharedFlow<EarthNasaResponse?> = MutableStateFlow(null)
    val imageList: Flow<EarthNasaResponse?> = _imageList
    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    fun requestPictureOfEarth() {

        viewModelScope.launch {
            _loading.emit(true)
            try {

                _imageList.emit(EarthNasaResponse(repository.pictureOfEath()))
            } catch (exc: IOException) {
                _error.emit("Network error")
            }
            _loading.emit(false)
        }

    }
}

class EarthViewModelFactory(val repository: NasaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = EarthPictureViewModel(repository) as T
}