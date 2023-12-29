package com.example.suitmediainternshiptest.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.suitmediainternshiptest.R
import com.example.suitmediainternshiptest.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = ""
        }
        toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val factory = AppViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        val adapter = UserAdapter(::onUserClick)
        binding.rvUser.adapter = adapter

        viewModel.users.observe(this) {pagingData ->
            adapter.submitData(lifecycle, pagingData)
        }

        adapter.addLoadStateListener {
            if(it.append.endOfPaginationReached){
                if(adapter.itemCount < 1){
                    showNotFound(true)
                }else{
                    showNotFound(false)
                }
            }
        }
    }

    private fun onUserClick(username: String) {
        viewModel.setSelectedUsername(username)
        Toast.makeText(this, "User selected", Toast.LENGTH_SHORT).show()
    }

    private fun showNotFound(isVisible: Boolean){
        if(isVisible){
            binding.notFound.visibility = View.VISIBLE
        }else{
            binding.notFound.visibility = View.GONE
        }
    }
}