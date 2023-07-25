package uz.kattabozor.test.presentation.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import uz.kattabozor.test.app.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import uz.kattabozor.test.domain.model.offer.Offer
import uz.kattabozor.test.domain.usecase.GetOffersUseCase
import uz.kattabozor.test.presentation.main.MainViewModel.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase
) : BaseViewModel<State, Input, Effect>() {

    init {
        processInput(Input.GetOffers)
    }

    data class State(
        val offers: List<Offer>? = null,
        val loading: Boolean = false,
        val error: Boolean = false,
    )

    sealed class Input {
        object GetOffers : Input()
    }

    class Effect

    override fun getInitialState() = State()

    override fun processInput(input: Input) {
        when (input) {
            Input.GetOffers -> getOffers()
        }
    }

    private fun getOffers() = getOffersUseCase()
            .stateOnStart { it.copy(loading = true, error = false) }
            .stateOnCatch { state, _ -> state.copy(error = true) }
            .stateOnEach { state, value -> state.copy(offers = value) }
            .stateOnComplete { it.copy(loading = false) }
            .launchIn(viewModelScope)
}