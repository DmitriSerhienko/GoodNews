package com.dimas.goodnews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.databinding.FragmentDetailsBinding
import com.dimas.goodnews.presentation.adapters.ArticleAdapter


class DetailsFragment(): Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentDetailsBinding == null")

    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val art = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(art.url)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}