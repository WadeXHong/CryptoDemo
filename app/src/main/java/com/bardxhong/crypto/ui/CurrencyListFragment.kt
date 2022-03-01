package com.bardxhong.crypto.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bardxhong.crypto.databinding.CurrencyListFragmentBinding
import kotlinx.coroutines.flow.collectLatest

class CurrencyListFragment : Fragment() {

    private var _binding: CurrencyListFragmentBinding? = null
    private val binding: CurrencyListFragmentBinding
        get() = _binding!!

    private val currencyViewModel by activityViewModels<CurrencyListViewModel>()
    private val adapter: CurrencyListAdapter?
        get() = _binding?.recycler?.adapter as? CurrencyListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CurrencyListFragmentBinding.inflate(inflater, container, false).let {
            _binding = it
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        lifecycleScope.launchWhenStarted {
            currencyViewModel.viewEntityStateFlow
                .collectLatest {
                    adapter?.updateList(it)
                }
        }
    }

    private fun setRecyclerView() {
        binding.recycler.adapter = CurrencyListAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CurrencyListFragment()
    }
}