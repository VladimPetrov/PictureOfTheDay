package ru.gb.pictureoftheday.ui.marsroverpicture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.gb.pictureoftheday.api.MarsRoverResponse
import ru.gb.pictureoftheday.domain.NasaRepository
import java.io.IOException

class MarsRoverPictureViewModel(val repository: NasaRepository) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loadind: Flow<Boolean> = _loading
    private val _imageList: MutableSharedFlow<MarsRoverResponse?> = MutableStateFlow(null)
    val imageList: Flow<MarsRoverResponse?> = _imageList
    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: Flow<String> = _error

    fun requestPictureOfMarsRover() {

        viewModelScope.launch {
            _loading.emit(true)
            try {
                _imageList.emit(repository.pictureOfMarsRover())
            } catch (exc: IOException) {
                _error.emit("Network error")
            }
            _loading.emit(false)
        }

    }
}

class MarsViewModelFactory(val repository: NasaRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = MarsRoverPictureViewModel(repository) as T
}