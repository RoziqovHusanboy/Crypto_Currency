package com.example.cryptocurrency.presenter

import android.app.ActionBar.LayoutParams
import android.graphics.Color
import android.os.Bundle
import android.text.PrecomputedText.Params
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.cryptocurrency.R
import com.example.cryptocurrency.common.UIEvent
import com.example.cryptocurrency.data.dto.CoinDetailModel
import com.example.cryptocurrency.databinding.CoinDetailLayoutBinding
import com.example.cryptocurrency.databinding.FragmentCoinListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private var _binding: CoinDetailLayoutBinding? = null
    val binding get() = _binding!!
    val viewModel: CoinDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CoinDetailLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val coin_id = arguments?.getString("coin_id")
        if (coin_id != null) {
            viewModel.getCoinDetail(coin_id)
        }
        lifecycleScope.launchWhenCreated {
            viewModel.coinDetail.collectLatest {
                when (it) {
                    UIEvent.Empty -> Unit
                    is UIEvent.Error<*> -> {
                        binding.detailLayout.isVisible = false
                        binding.progressbar.isVisible = false
                        binding.errorTv.isVisible = true
                        binding.errorTv.text = it.massage
                    }

                    UIEvent.Loading -> {
                    binding.progressbar.isVisible = true
                        binding.detailLayout.isVisible =false
                        binding.errorTv.isVisible = false
                    }

                    is UIEvent.Success<*> -> {
                        binding.detailLayout.isVisible = true
                        binding.progressbar.isVisible = false
                        binding.errorTv.isVisible = false
                        populateData(it.data as CoinDetailModel)
                    }
                }
            }

        }

    }

    private fun populateData(coinDetail: CoinDetailModel) {
        Glide.with(requireContext()).load(coinDetail.logo).into(binding.imageView)
        binding.coinNameTv.text = "${coinDetail.rank}. ${coinDetail.name}(${coinDetail.symbol})"
        binding.statusTv.text = if (coinDetail.is_active) "Active" else "Not Active"
        binding.statusTv.setTextColor(if (coinDetail.is_active) Color.GREEN else Color.RED)
        binding.descriptionTv.text = coinDetail.description

        if (!coinDetail.tags.isNullOrEmpty()){
            coinDetail.tags.forEach{
                binding.flow.addView(getTagTextView(it.name))
            }
        }
    }

    private fun getTagTextView(tagString: String): TextView {
        val textview = TextView(requireContext())
        textview.text = tagString
        textview.setTextColor(Color.GREEN)
        textview.layoutParams =
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT )
        textview.setPadding(30,20,30,20)
        textview.setBackgroundResource(R.drawable.bg_tag)
        return textview
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}