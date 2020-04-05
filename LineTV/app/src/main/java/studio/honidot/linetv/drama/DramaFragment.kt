package studio.honidot.linetv.drama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import studio.honidot.linetv.databinding.FragmentDramaBinding
import studio.honidot.linetv.extension.getVmFactory

class DramaFragment : Fragment() {

    /**
     * Lazily initialize our [DramaViewModel].
     */
    private val viewModel by viewModels<DramaViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDramaBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.recyclerHome.adapter = DramaAdapter(DramaAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshStatus.observe(this, Observer {
            it?.let {
                binding.layoutSwipeRefreshHome.isRefreshing = it
            }
        })

//        viewModel.navigateToDetail.observe(this, Observer {
//            it?.let {
//                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
//                viewModel.onDetailNavigated()
//            }
//        })

        return binding.root
    }

}