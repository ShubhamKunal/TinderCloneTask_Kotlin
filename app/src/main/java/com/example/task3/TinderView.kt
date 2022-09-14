package com.example.task3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TinderView: ViewModel()
{
    private val stream= MutableLiveData<Tinder>()

    val modelStream:LiveData<Tinder>
        get() = stream

    private val data = listOf(
        TinderCard(
            profilePhoto = R.drawable.modi,
            name = "Narendra Modi",
            bio ="Prime Minister of India",
        ),
        TinderCard(
            profilePhoto = R.drawable.raga,
            name = "Rahul Gandhi",
            bio ="Member of Gandhi Family",
        ),TinderCard(
            profilePhoto = R.drawable.shah,
            name = "Amit Shah",
            bio ="Home Minister of India",
        )
    )
    private var currentIndex = 0

    private val topCard
        get() = data[currentIndex % data.size]
    private val bottomCard
        get() = data[(currentIndex + 1) % data.size]

    fun swipe() {
        currentIndex += 1
        updateStream()
    }

    private fun updateStream() {
        stream.value = Tinder(
            top = topCard,
            bottom = bottomCard
        )
    }
    init {
        updateStream()
    }
}