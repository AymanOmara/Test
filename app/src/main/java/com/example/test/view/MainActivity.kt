package com.example.test.view


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.ActivityMainBinding
import com.example.test.dto.UserCredentials
import com.example.test.model.Repository
import com.example.test.viewmodel.ViewModel
import com.example.test.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel:ViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val repository = Repository()
            viewModelFactory = ViewModelFactory(repository)
            viewModel = ViewModelProvider(this,viewModelFactory).get(ViewModel::class.java)
            viewModel.intailizeContext(this)
            viewModel.live.observe(this){
                when(it){
                    ViewModel.LogInCases.startLoading ->{
                        binding.progressBar.alpha = 1f
                    }
                    ViewModel.LogInCases.stopLoading ->{
                        binding.progressBar.alpha = 0f
                    }
                    ViewModel.LogInCases.success ->{

                    }
                    ViewModel.LogInCases.message -> {

                    }
                }
            }
            binding.button.setOnClickListener {
                val email = binding.emailet.text.toString()
                val password = binding.passwordet.text.toString()
                if(!email.isEmpty() && !password.isEmpty()){
                    viewModel.callAPI(logInData = UserCredentials(email,password))
                    binding.emailet.error = null
                    binding.passwordet.error = null
                }else if (email.isEmpty()){
                    binding.emailet.setError("required field")
                }else if (password.isEmpty()){
                    binding.passwordet.setError("required field")
                }
            }
        }
    }
