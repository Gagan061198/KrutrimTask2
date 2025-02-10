package com.example.krutrimtask1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlin.math.log2
import kotlin.math.pow

    class PerplexityVM : ViewModel() {

        private val probabilities = MutableStateFlow<List<Double>>(emptyList())
        val exposedprobabilities: StateFlow<List<Double>> = probabilities.asStateFlow()

        val perplexity: StateFlow<Double> = exposedprobabilities
            .map { probs ->
                if (probs.isEmpty()) 0.0
                else {
                    val logSum = probs.sumOf { -log2(it) }
                    2.0.pow(logSum / probs.size)
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = 0.0
            )

        fun addProbability(probability: Double) {
            if (probability in 0.0..1.0) {
                probabilities.update { it.toMutableList().apply { add(probability) } }
            }
        }
    }

