package com.example.suitmediainternshiptest.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.example.suitmediainternshiptest.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.isEnabled = false
        binding.btnCheckPalindrome.isEnabled = false

        binding.edPalindrome.addTextChangedListener {
            binding.btnCheckPalindrome.isEnabled = it.toString().isNotEmpty()
        }

        binding.edName.addTextChangedListener {
            binding.btnNext.isEnabled = it.toString().isNotEmpty()
        }

        binding.btnCheckPalindrome.setOnClickListener {
            val inputtext = binding.edPalindrome.text.toString()
            if(isPalindrome(inputtext)){
                showResultDialog("It's Palindrome")
            }else{
                showResultDialog("Not palindrome")
            }
        }

        binding.btnNext.setOnClickListener {
            val name = binding.edName.text.toString()
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.EXTRA_NAME, name)
            startActivity(intent)
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val cleanText = text.replace("[^A-Za-z0-9]".toRegex(), "").lowercase(Locale.ROOT)

        return cleanText == cleanText.reversed()
    }

    private fun showResultDialog(resultMessage: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Palindrom Result")
        alertDialogBuilder.setMessage(resultMessage)

        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
