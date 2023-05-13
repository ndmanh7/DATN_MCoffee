package com.example.mcoffee.ui.fragment.admin

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mcoffee.R
import com.example.mcoffee.data.model.Product
import com.example.mcoffee.databinding.FragmentSearchBinding
import com.example.mcoffee.ui.adapter.SearchAdapter
import com.example.mcoffee.ui.base.BaseFragment
import com.example.mcoffee.ui.viewmodel.SearchViewModel
import com.example.mcoffee.utils.setQueryTextChangeStateFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdminSearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var mSearchAdapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSearchAdapter = SearchAdapter()
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun bindView() {
        super.bindView()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                binding.edtSearch.setQueryTextChangeStateFlow()
                    //wait for user to finish typing, then search -> avoid too many unnecessary APIs call
                    .debounce(300)

                    //filter empty string
                    .filter { queryString ->
                        return@filter queryString.isNotEmpty()
                    }

                    //avoid duplicate network calls
                    .distinctUntilChanged()
                    .flatMapLatest { queryString ->
                        searchViewModel.searchProduct(queryString)
                    }.flowOn(Dispatchers.IO)
                    .collect { resultList ->
                        setUpAdapter(resultList)
                    }
            }
        }
    }

    private fun setUpAdapter(resultList: List<Product>) {
        mSearchAdapter.submitList(resultList)
        binding.recyclerViewSearchResult.apply {
            adapter = mSearchAdapter
            layoutManager = LinearLayoutManager(requireContext())
            mSearchAdapter.setOnItemClickListener {
                val action = AdminSearchFragmentDirections.actionAdminSearchFragmentToAdminEditProductFragment(it)
                findNavController().navigate(action)
            }
        }
    }

}