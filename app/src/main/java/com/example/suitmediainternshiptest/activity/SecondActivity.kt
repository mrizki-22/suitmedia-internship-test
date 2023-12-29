package com.example.suitmediainternshiptest.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.suitmediainternshiptest.R
import com.example.suitmediainternshiptest.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)

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

        binding.tvName.text = name

        binding.btnChoose.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        val factory = AppViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[AppViewModel::class.java]

        viewModel.getSelectedUsername()
        viewModel.selectedUsername.observe(this) {
            if (it.isNotEmpty()) {
                binding.tvSelectedUsername.text = it
            }
        }

    }


    companion object {
        const val EXTRA_NAME = "extra_name"
    }
}