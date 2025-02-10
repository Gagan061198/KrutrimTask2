package com.example.krutrimtask1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.krutrimtask1.viewModel.PerplexityVM
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private  lateinit var viewModel: PerplexityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_screen)

        val editText = findViewById<EditText>(R.id.editText)
        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.submitButton)

        viewModel = ViewModelProvider(this).get(PerplexityVM::class.java)

        button.setOnClickListener{
            val input : String = editText.text.toString()
            val inputProb = input.toDoubleOrNull()

            if(inputProb != null && inputProb in 0.0..1.0) {
                viewModel.addProbability(inputProb)
            }else {
                Toast.makeText(this, "Wrong input, kindly enter values between 0.0 and 1.0 only", Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launch {
            viewModel.perplexity.collect{
                    perplexity ->
                textView.text = "Perplexity is : $perplexity"
            }
        }

    }
}