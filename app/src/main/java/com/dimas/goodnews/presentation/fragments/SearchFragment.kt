package com.dimas.goodnews.presentation.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimas.goodnews.R
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.databinding.FragmentSearchBinding
import com.dimas.goodnews.utils.Resource
import com.dimas.goodnews.presentation.MainActivity
import com.dimas.goodnews.presentation.adapters.ArticleAdapter
import com.dimas.goodnews.presentation.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentSearchBinding == null")

    lateinit var newsAdapter: ArticleAdapter
    lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        var job: Job? = null
        ed_search.addTextChangedListener { text: Editable? ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.getSearchNews(query = it.toString())
                    }
                }
            }
        }
        initAdapter()
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner) { responce ->
            when (responce) {
                is Resource.Success -> {
                    binding.pagSearchPgBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        newsAdapter.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    binding.pagSearchPgBar.visibility = View.INVISIBLE
                    responce.data?.let {
                        Log.d("MyLog", "MainFragError: ${it}")
                    }
                }
                is Resource.Loading -> {
                    binding.pagSearchPgBar.visibility = View.VISIBLE
                }
            }
            newsAdapter.onArticleClickListener = {
                launchDetailFragment(it)
            }
            newsAdapter.onSaveClickListener = {
                if (it.id == null) {
                    it.id = Random.nextInt(0, 1000)
                }
                save(it)
            }
        }
    }

    private fun save(article: Article) {
        viewModel.saveArticle(article)
    }

    private fun launchDetailFragment(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_searchFragment_to_detailsFragment,
            bundle
        )
    }

    private fun initAdapter() {
        newsAdapter = ArticleAdapter(requireContext())
        search_news_adapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}