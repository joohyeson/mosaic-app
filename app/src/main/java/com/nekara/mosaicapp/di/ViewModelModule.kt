package com.nekara.mosaicapp.di

import com.nekara.mosaicapp.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
}