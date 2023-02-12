package com.dimas.goodnews.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.goodnews.R
import com.dimas.goodnews.databinding.FragmentStartBinding
import com.dimas.goodnews.presentation.MainActivity
import com.dimas.goodnews.presentation.adapters.ArticleAdapter
import com.dimas.goodnews.utils.Resource
import kotlinx.android.synthetic.main.fragment_start.*


class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentStartBinding == null")

    lateinit var newsAdapter: ArticleAdapter

    lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel = (activity as MainActivity).viewModel
        viewModel.startNews.observe(viewLifecycleOwner) { responce ->
            when (responce) {
                is Resource.Success -> {
                    pag_pr_bar.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsAdapter.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    pag_pr_bar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.d("MyLog", "MainFragError: ${it}")
                    }
                }
                is Resource.Loading -> {
                    pag_pr_bar.visibility = View.VISIBLE
                }
            }

        }
    }
    private fun initAdapter() {
        newsAdapter = ArticleAdapter(requireContext())
        news_adapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}