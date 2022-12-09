package com.example.zadanie.ui.viewmodels

import androidx.lifecycle.*
import com.example.zadanie.data.DataRepository
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.helpers.Evento
import com.example.zadanie.helpers.Sort
import com.example.zadanie.helpers.SortMethod
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val user = liveData { emitSource(repository.dbUser()) }

    private val _sortingSwitchAsc = MutableLiveData(false)
    val sortSwitchAsc: LiveData<Boolean>
        get() = _sortingSwitchAsc

    private val _sort: MutableLiveData<Sort> = MutableLiveData(
        Sort(SortMethod.VISITORS, false)
    )
    val sort: LiveData<Sort>
        get() = _sort

    val bars: LiveData<List<BarItem>?> = Transformations.switchMap(_sort) {
        liveData {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
            _sort.value?.let {
                emitSource(repository.dbBars(it))
            }
        }
    }

    fun refreshData(){
        viewModelScope.launch {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
        }
    }

    fun show(msg: String){ _message.postValue(Evento(msg))}

    fun setSortingDirection(asc: Boolean) {
        _sortingSwitchAsc.value = asc
        val sortMethod = _sort.value?.sortMethod
        _sort.value = sortMethod?.let { Sort(it, asc) }
    }

    fun setSortingMethod(sort: SortMethod) {
        val asc = _sort.value?.asc
        _sort.value = asc?.let { Sort(sort, it) }
    }
}