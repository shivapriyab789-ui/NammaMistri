package com.example.nammamistri.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.nammamistri.data.Photo
import com.example.nammamistri.data.PhotoDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PhotosViewModel(private val photoDao: PhotoDao) : ViewModel() {

    val allPhotos: Flow<List<Photo>> = photoDao.getAllPhotos()

    fun addPhoto(uri: String, label: String? = null) {
        viewModelScope.launch {
            photoDao.insertPhoto(Photo(uri = uri, label = label))
        }
    }

    fun deletePhoto(photo: Photo) {
        viewModelScope.launch {
            photoDao.deletePhoto(photo)
        }
    }
}

class PhotosViewModelFactory(private val photoDao: PhotoDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhotosViewModel(photoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
