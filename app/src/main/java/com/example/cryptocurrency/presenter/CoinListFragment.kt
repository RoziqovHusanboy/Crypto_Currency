package com.example.cryptocurrency.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cryptocurrency.common.UIEvent
import com.example.cryptocurrency.data.dto.coinModel
import com.example.cryptocurrency.databinding.FragmentCoinListBinding
import com.example.cryptocurrency.utils.CoinListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CoinListFragment : Fragment() {
    private var _binding:FragmentCoinListBinding? =  null
    private val binding get() = _binding!!
    val viewModel:CoinListViewModel by viewModels()
    private lateinit var  coinListAdapter: CoinListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getCoinList()
        coinListAdapter = CoinListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinListBinding.inflate(layoutInflater,container,false)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = coinListAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())


        lifecycleScope.launchWhenCreated {
            viewModel.coinList.collectLatest {
                when(it){
                    UIEvent.Empty -> Unit
                    is UIEvent.Error<*> -> {
                        binding.progressbar.isVisible = false
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = it.massage
                        binding.recyclerView.isVisible = false
                    }
                    UIEvent.Loading -> {
                        binding.progressbar.isVisible = true
                        binding.errorTv.isVisible = false
                        binding.recyclerView.isVisible = false
                    }
                    is UIEvent.Success<*> -> {
                        binding.progressbar.isVisible =false
                        binding.errorTv.isVisible = false
                        binding.recyclerView.isVisible = true
                    val coinlist = it.data as List<coinModel>
                        coinListAdapter.submitList(coinlist)
                        coinListAdapter.notifyItemChanged(coinlist.size)
                    }
                }
            }
        }
    }


}