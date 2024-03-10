package com.mfo.todoapp.ui.home.modal

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.mfo.todoapp.databinding.ModalBinding
import com.mfo.todoapp.ui.home.modal.adapter.UserAdapter
import com.mfo.todoapp.utils.UserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ModalDialogFragment: DialogFragment() {

    private lateinit var binding: ModalBinding
    private val modalDialogViewModel: ModalDialogViewModel by viewModels()
    private lateinit var userAdapter: UserAdapter

    companion object {
        fun newInstance(todoId: Long): ModalDialogFragment {
            val fragment = ModalDialogFragment()
            val args = Bundle()
            args.putLong("todoId", todoId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.BOTTOM)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val todoId = requireArguments().getLong("todoId")
        initUIState(todoId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ModalBinding.inflate(layoutInflater, container, false)
        /*val usersList = modalDialogViewModel.getAllUsers(token, todoId)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewModal)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val userAdapter = UserAdapter(usersList) // Usa tu adaptador de usuario
        recyclerView.adapter = userAdapter*/
        return binding.root
    }

    private fun initList() {
        binding.recyclerViewModal.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    private fun initUIState(todoId: Long) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val token: String = UserData.token
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