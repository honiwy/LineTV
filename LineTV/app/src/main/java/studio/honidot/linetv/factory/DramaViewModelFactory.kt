package studio.honidot.linetv.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.data.source.LineTVRepository
import studio.honidot.linetv.detail.DetailViewModel

@Suppress("UNCHECKED_CAST")
class DramaViewModelFactory(
    private val lineTVRepository: LineTVRepository,
    private val drama: Drama

) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(DetailViewModel::class.java) ->
                    DetailViewModel(lineTVRepository, drama)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}