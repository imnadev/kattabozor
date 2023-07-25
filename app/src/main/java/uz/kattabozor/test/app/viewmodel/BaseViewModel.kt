package uz.kattabozor.test.app.viewmodel

import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*

abstract class BaseViewModel<State : Any, Input : Any, Effect : Any>() :
    Store<State, Input, Effect>, ViewModel() {

    private val effectsChannel = Channel<Effect>(Channel.BUFFERED)

    private val stateFlow by lazy(LazyThreadSafetyMode.NONE) {
        MutableStateFlow(getInitialState())
    }

    override val state: StateFlow<State>
        get() = stateFlow

    override val effects: Flow<Effect>
        get() = effectsChannel.receiveAsFlow()

    protected fun getState(): State = stateFlow.value

    abstract fun getInitialState(): State

    fun emitEffect(effect: Effect) {
        effectsChannel.trySendBlocking(effect)
    }

    @MainThread
    protected fun emitState(state: State): State {
        require(Looper.getMainLooper() == Looper.myLooper()) {
            "Must be running on main thread"
        }
        stateFlow.compareAndSet(stateFlow.value, state)
        return stateFlow.value
    }

    @MainThread
    fun updateState(update: (state: State) -> State): State {
        val newState = update(getState())
        return emitState(newState)
    }

    protected fun <T> Flow<T>.stateOnStart(action: (state: State) -> State) = onStart {
        updateState { action(it) }
    }

    protected fun <T> Flow<T>.stateOnComplete(action: (state: State) -> State) = onCompletion {
        updateState { action(it) }
    }

    protected fun <T> Flow<T>.stateOnEach(action: (state: State, value: T) -> State) =
        onEach { value ->
            updateState { action(it, value) }
        }

    protected fun <T> Flow<T>.effectOnCatch(action: (e: Throwable) -> Effect) = catch {
        emitEffect(action(it))
    }

    protected fun <T> Flow<T>.effectOnEach(action: (value: T) -> Effect) = onEach {
        emitEffect(action(it))
    }

    protected fun <T> Flow<T>.stateOnCatch(action: (state: State, e: Throwable) -> State) = catch {
        updateState { state -> action(state, it) }
    }
}