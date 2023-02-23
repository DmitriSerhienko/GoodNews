package com.dimas.goodnews.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dimas.goodnews.R
import com.dimas.goodnews.data.network.models.Article
import com.dimas.goodnews.databinding.FragmentSaveBinding
import com.dimas.goodnews.presentation.MainActivity
import com.dimas.goodnews.presentation.adapters.ArticleAdapter
import com.dimas.goodnews.presentation.viewmodels.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SaveFragment : Fragment() {
    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("FragmentSaveBinding == null")

    lateinit var newsAdapter: ArticleAdapter
    lateinit var viewModel: NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        initAdapter()
        newsAdapter.onArticleClickListener = {
            launchDetailFragment(it)
        }
        newsAdapter.onSaveClickListener = {
            save(it)
        }

        val itemTouchHelperCall = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(view, " Successfully deleted article", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo") {
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCall).apply {
            attachToRecyclerView(binding.rvSavedNews)
        }

        viewModel.getSavedArticles().observe(viewLifecycleOwner, Observer {
            newsAdapter.submitList(it)
        })

    }


    private fun save(article: Article) {
        viewModel.saveArticle(article)
    }

    private fun launchDetailFragment(article: Article) {
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }
        findNavController().navigate(
            R.id.action_saveFragment_to_detailsFragment,
            bundle
        )
    }

    private fun initAdapter() {
        newsAdapter = ArticleAdapter(requireContext())
        binding.rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }


}


