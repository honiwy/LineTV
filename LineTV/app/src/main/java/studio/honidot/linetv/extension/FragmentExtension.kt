package studio.honidot.linetv.extension

import androidx.fragment.app.Fragment
import studio.honidot.linetv.LineTVApplication
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.factory.DramaViewModelFactory
import studio.honidot.linetv.factory.ViewModelFactory

/**
 * Extension functions for Fragment.
 */
fun Fragment.getVmFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as LineTVApplication).lineTVRepository
    return ViewModelFactory(repository)
}

fun Fragment.getVmFactory(drama:Drama): DramaViewModelFactory {
    val repository = (requireContext().applicationContext as LineTVApplication).lineTVRepository
    return DramaViewModelFactory(repository, drama)
}