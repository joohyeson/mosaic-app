package com.nekara.mosaicapp.ui.main

import android.view.MenuItem
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nekara.mosaicapp.R
import com.nekara.mosaicapp.mlkit.vision.VisionType

class MainViewModel : ViewModel() {

    val onItemSelectedEvent: MutableLiveData<VisionType> = MutableLiveData()
    val onFabButtonEvent: MutableLiveData<Unit?> = MutableLiveData()
    val onShutterButtonEvent: MutableLiveData<Unit?> = MutableLiveData()

    fun onBottomMenuClicked(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_object_detect -> postVisionType(VisionType.Object)
            R.id.action_ocr -> postVisionType(VisionType.OCR)
        }
        item.isChecked = true
        return false
    }

    fun onClickFabButton(view: View) {
        onFabButtonEvent.postValue(Unit)
    }

    fun onClickShutter(view: View) {
        onShutterButtonEvent.postValue(Unit)
    }

    private fun postVisionType(type: VisionType) {
        onItemSelectedEvent.postValue(type)
    }

}