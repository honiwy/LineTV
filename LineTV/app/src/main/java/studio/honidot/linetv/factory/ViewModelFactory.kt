package studio.honidot.linetv.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.honidot.linetv.drama.DramaViewModel
import studio.honidot.linetv.data.source.LineTVRepository

/**
 * Factory for all ViewModels.
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val lineTVRepository: LineTVRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(DramaViewModel::class.java) ->
                    DramaViewModel(lineTVRepository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}