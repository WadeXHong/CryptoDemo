package com.bardxhong.crypto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bardxhong.crypto.databinding.CurrencyListFragmentBinding

class CurrencyListFragment : Fragment() {

    private var _binding: CurrencyListFragmentBinding? = null
    private val binding: CurrencyListFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return CurrencyListFragmentBinding.inflate(inflater, container, false).let {
            _binding = it
            it.root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = CurrencyListFragment()
    }
}