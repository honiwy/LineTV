package studio.honidot.linetv.drama

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import studio.honidot.linetv.NavigationDirections
import studio.honidot.linetv.R
import studio.honidot.linetv.SearchManager
import studio.honidot.linetv.bindApiErrorMessage
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.databinding.FragmentDramaBinding
import studio.honidot.linetv.extension.getVmFactory
import studio.honidot.linetv.utility.Logger
import studio.honidot.linetv.utility.Util


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
                viewModel.dramasSorted.value?.let {
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
                SearchManager.userSearch = newText
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }
        })
        binding.searchView.setQuery(SearchManager.userSearch, false)

        binding.layoutSwipeRefreshHome.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.dramas.observe(this, Observer {
            if (it.isNullOrEmpty()) {
                bindApiErrorMessage(
                    binding.textError, Util.getString(R.string.internet_refresh)
                )
            }

        })

        viewModel.isDramasPrepared.observe(viewLifecycleOwner, Observer {
        })

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

        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.condition_menu, menu)

    }

    /**
     * Updates the order in the [DramaViewModel] when the menu items are selected from the
     * overflow menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        viewModel.updateOrder(
            when (item.itemId) {
                R.id.rating_menu -> SortCondition.RATING
                R.id.total_view_menu -> SortCondition.TOTAL_VIEW
                else -> SortCondition.DEFAULT
            }
        )
        return true
    }
}