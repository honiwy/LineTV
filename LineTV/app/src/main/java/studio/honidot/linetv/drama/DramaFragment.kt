package studio.honidot.linetv.drama

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.databinding.FragmentDramaBinding
import studio.honidot.linetv.extension.getVmFactory
import androidx.navigation.fragment.findNavController
import studio.honidot.linetv.NavigationDirections


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

        val dramaAdapter = DramaAdapter(DramaAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })

        binding.recyclerHome.adapter = dramaAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val resultList = mutableListOf<Drama>()
                viewModel.dramas.value?.let {
                    if (newText.isNullOrEmpty()) {
                        dramaAdapter.submitList(it)
                    } else {
                        for (drama in it) {
                            if (drama.name.contains(newText.toString())) {
                                resultList.add(drama)
                            }
                        }
                        dramaAdapter.submitList(resultList)
                    }
                }
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean { // Do your task here
                return false
            }
        })


        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.refreshStatus.observe(this, Observer {
            it?.let {
                binding.layoutSwipeRefreshHome.isRefreshing = it
            }
        })

        viewModel.navigateToDetail.observe(this, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
                viewModel.onDetailNavigated()
            }
        })

        return binding.root
    }

}