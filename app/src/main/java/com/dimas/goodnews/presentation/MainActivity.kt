package com.dimas.goodnews.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.dimas.goodnews.R
import com.dimas.goodnews.data.db.Repository
import com.dimas.goodnews.data.network.NewsRepositoryImpl
import com.dimas.goodnews.databinding.ActivityMainBinding
import com.dimas.goodnews.presentation.viewmodels.NewsViewModel
import com.dimas.goodnews.presentation.viewmodels.NewsViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding ?: throw RuntimeException("ActivityMainBinding == null")

    lateinit var viewModel: NewsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Repository.init(applicationContext)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val newsRepository = NewsRepositoryImpl()
        val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
        viewModel = ViewModelProvider(
            this@MainActivity,
            viewModelProviderFactory
        ).get(NewsViewModel::class.java)

        bottom_nav_menu.setupWithNavController(
            navController = navController
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}