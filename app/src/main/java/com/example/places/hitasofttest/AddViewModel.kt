package com.example.places.hitasofttest

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddViewModel @Inject constructor() : ViewModel() {

    private val singleVideoId = "6-N8cH46OEY"

    val displayedVideoIds = mutableStateListOf<String>()

    fun addNextVideo() {
        displayedVideoIds.add(singleVideoId) // Adds duplicate on every call
    }
}