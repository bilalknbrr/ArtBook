package com.example.artbook2

import androidx.lifecycle.ViewModel

class ArtViewModel: ViewModel() {

    val artList = mutableListOf<ArtModel>()

    fun addArt(art: ArtModel) {
        artList.add(art)
    }
}