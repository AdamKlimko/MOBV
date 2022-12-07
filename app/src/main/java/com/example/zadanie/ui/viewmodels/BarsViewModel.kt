package com.example.zadanie.ui.viewmodels

import androidx.lifecycle.*
import com.example.zadanie.data.DataRepository
import com.example.zadanie.data.db.model.BarItem
import com.example.zadanie.data.db.model.UserItem
import com.example.zadanie.helpers.Evento
import com.example.zadanie.helpers.Sort
import kotlinx.coroutines.launch

class BarsViewModel(private val repository: DataRepository): ViewModel() {
    private val _message = MutableLiveData<Evento<String>>()
    val message: LiveData<Evento<String>>
        get() = _message

    val loading = MutableLiveData(false)

    val user = liveData { emitSource(repository.dbUser()) }

//    val bars: LiveData<List<BarItem>?> =
//        liveData {
//            loading.postValue(true)
//            repository.apiBarList { _message.postValue(Evento(it)) }
//            loading.postValue(false)
//            emitSource(repository.dbBars())
//        }

    private val _sortingMethod: MutableLiveData<Sort> = MutableLiveData(Sort.VISITORS)

    val bars: LiveData<List<BarItem>?> = Transformations.switchMap(_sortingMethod) {
        liveData {
            loading.postValue(true)
            repository.apiBarList { _message.postValue(Evento(it)) }
            loading.postValue(false)
            when (_sortingMethod.value) {
                Sort.NAME_ASC -> { emitSource(repository.dbBars(Sort.NAME_ASC)) }
                Sort.NAME_DESC -> { emitSource(repository.dbBars(Sort.NAME_DESC)) }
                Sort.VISITORS -> { emitSource(repository.dbBars(Sort.VISITORS)) }
                else -> {}
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

    fun setSortingMethod(sort: Sort) {
        _sortingMethod.value = sort
    }
}