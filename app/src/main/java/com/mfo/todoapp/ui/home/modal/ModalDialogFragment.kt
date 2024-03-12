package com.mfo.todoapp.ui.home.modal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mfo.todoapp.R
import com.mfo.todoapp.databinding.ModalBinding
import com.mfo.todoapp.ui.home.modal.adapter.UserAdapter
import com.mfo.todoapp.utils.PreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModalDialogFragment: BottomSheetDialogFragment() {

    private lateinit var binding: ModalBinding
    private val modalDialogViewModel: ModalDialogViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    companion object {
        fun newInstance(todoId: Long,name: String, lastName: String, task: String): ModalDialogFragment {
            val fragment = ModalDialogFragment()
            val args = Bundle()
            args.putLong("todoId", todoId)
            args.putString("name", name)
            args.putString("lastName", lastName)
            args.putString("task", task)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoId = requireArguments().getLong("todoId")
        val name = requireArguments().getString("name")
        val lastName = requireArguments().getString("lastName")
        val task = requireArguments().getString("task")
        initUIState(todoId)
        initListeners()
        val titleText = "Hello ${"$name $lastName"} share your todo"
        binding.tvTitle.text = titleText
        binding.tvTaskTodo.text = "\"$task\""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ModalBinding.inflate(layoutInflater, container, false)
        userAdapter = UserAdapter(emptyList())
        binding.recyclerViewModal.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = userAdapter
        }
        return binding.root
    }

    private fun initListeners() {
        binding.etEmailShared.addTextChangedListener { userEmail ->
            if(userEmail.toString().isNotEmpty()) {
                sharedTodo()
                binding.btnShare.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            } else {
                binding.btnShare.setTextColor(ContextCompat.getColor(requireContext(), R.color.gray))
            }
        }
    }

    private fun sharedTodo() {
        binding.btnShare.setOnClickListener() {
            val preferences = PreferenceHelper.defaultPrefs(binding.btnShare.context)
            val token: String = preferences.getString("jwt", "").toString()
            val userEmail: String = binding.etEmailShared.text.toString()
            val todoId = requireArguments().getLong("todoId")
            if(userEmail.isNotEmpty()) {
                modalDialogViewModel.addTodoShared(token, todoId, userEmail)
                binding.etEmailShared.setText("")

                val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etEmailShared.windowToken, 0)
            }
        }
    }

    private fun initUIState(todoId: Long) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val preferences = PreferenceHelper.defaultPrefs(requireContext())
                val token: String = preferences.getString("jwt", "").toString()
                modalDialogViewModel.getAllUsers(token, todoId)

                modalDialogViewModel.state.collect{
                    when(it) {
                        is ModalState.Error -> {
                            errorState(it.error)
                        }
                        ModalState.Loading -> loadingState()
                        is ModalState.Success -> {
                            successSate(it)
                            /*if (it.users.isEmpty()) {

                            }*/
                        }
                    }
                }
            }
        }
    }

    private fun loadingState() {
        //binding.pb.isVisible = true
    }

    private fun errorState(error: String) {
        //binding.pb.isVisible = false
        val context = binding.root.context
        Toast.makeText(context, "Error: $error", Toast.LENGTH_SHORT).show()
        if (error == "Unauthorized: invalid token") {
            logOut()
        }
    }

    private fun successSate(state: ModalState.Success) {
        //binding.pb.isVisible = false
        //userAdapter.updateData(state.users)
        //binding.recyclerViewModal.adapter = userAdapter

        binding.recyclerViewModal.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewModal.adapter = UserAdapter(state.users)

    }

    private fun logOut() {
        //deslogear
    }
}