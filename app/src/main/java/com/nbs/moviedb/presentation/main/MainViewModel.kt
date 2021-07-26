package com.nbs.moviedb.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by aydbtiko on 7/25/2021.
 *
 */
class MainViewModel : ViewModel() {

    private val _bottomNavVisible = MutableLiveData(true)
    val bottomNavVisible: LiveData<Boolean> = _bottomNavVisible

    fun togglesBottomNavVisibility(visible: Boolean) {
        _bottomNavVisible.value = visible
    }

}
