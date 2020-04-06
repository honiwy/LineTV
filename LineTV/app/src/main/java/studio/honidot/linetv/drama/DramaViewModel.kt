package studio.honidot.linetv.drama

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import studio.honidot.linetv.LineTVApplication
import studio.honidot.linetv.R
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.data.Result
import studio.honidot.linetv.data.source.LineTVRepository
import studio.honidot.linetv.network.LoadApiStatus
import studio.honidot.linetv.utility.Logger
import studio.honidot.linetv.utility.Util
import studio.honidot.linetv.utility.Util.getString

class DramaViewModel(private val lineTVRepository: LineTVRepository) : ViewModel() {

    val dramas: LiveData<List<Drama>> = lineTVRepository.getDramasInLocal()

    val isDramasPrepared = MediatorLiveData<Boolean>().apply {
        addSource(dramas) {
            value = !dramas.value.isNullOrEmpty()
            if (value == true) {
                _dramasSorted.value = dramas.value
            }
        }
    }
    private val _dramasSorted = MutableLiveData<List<Drama>>()
    val dramasSorted: LiveData<List<Drama>>
        get() = _dramasSorted

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Drama>()

    val navigateToDetail: LiveData<Drama>
        get() = _navigateToDetail

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * Call getDramas() on init so we can display status immediately.
     */
    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getDramas(true)
    }

    private fun getDramas(isInitial: Boolean = false) {

        coroutineScope.launch {

            if (!Util.isInternetConnected()) {
                Toast.makeText(
                    LineTVApplication.INSTANCE.applicationContext,
                    getString(R.string.internet_not_connected),
                    Toast.LENGTH_SHORT
                ).show()
                if (isInitial) _status.value = LoadApiStatus.ERROR

            } else {

                if (isInitial) _status.value = LoadApiStatus.LOADING
                // It will return Result object after Deferred flow

                when (val result = lineTVRepository.getDramas()) {
                    is Result.Success -> {
                        _error.value = null
                        if (isInitial) _status.value = LoadApiStatus.DONE
                        result.data.dramaList?.forEach {
                            lineTVRepository.insertDrama(it)
                        }
                    }
                    is Result.Fail -> {
                        _error.value = result.error
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                    }
                    is Result.Error -> {
                        _error.value = result.exception.toString()
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                    }
                    else -> {
                        _error.value = getString(R.string.you_know_nothing)
                        if (isInitial) _status.value = LoadApiStatus.ERROR
                    }
                }
            }
            _refreshStatus.value = false

        }
    }

    fun refresh() {
        if (status.value != LoadApiStatus.LOADING) {
            getDramas()
        }
    }

    fun navigateToDetail(drama: Drama) {
        _navigateToDetail.value = drama
    }

    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

    fun updateOrder(sort: SortCondition) {
        dramas.value?.let { dramaList ->
            when (sort) {
                SortCondition.RATING -> _dramasSorted.value = dramaList.sortedByDescending { it.rating }
                SortCondition.TOTAL_VIEW -> _dramasSorted.value = dramaList.sortedByDescending { it.totalViews }
                else -> _dramasSorted.value = dramaList.sortedBy { it.dramaId }
            }
        }
    }
}