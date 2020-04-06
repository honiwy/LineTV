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
import studio.honidot.linetv.UserManager
import studio.honidot.linetv.bindApiErrorMessage
import studio.honidot.linetv.data.Drama
import studio.honidot.linetv.databinding.FragmentDramaBinding
import studio.honidot.linetv.extension.getVmFactory
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
                UserManager.userSearch = query
                return false
            }
        })
        binding.searchView.setQuery(UserManager.userSearch, false)

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
                R.id.rating_menu -> OrderCondition.RATING
                R.id.created_at_menu -> OrderCondition.CREATED_TIME
                R.id.total_view_menu -> OrderCondition.TOTAL_VIEW
                else -> OrderCondition.TOTAL_VIEW
            }
        )
        return true
    }
}