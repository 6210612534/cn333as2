package com.example.listmaker.ui.detail

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.listmaker.ListDetailActivity
import com.example.listmaker.MainActivity
import com.example.listmaker.R
import com.example.listmaker.databinding.ListDetailFragmentBinding
import com.example.listmaker.models.TaskList
import java.lang.RuntimeException

class ListDetailFragment : Fragment() {

    lateinit var binding: ListDetailFragmentBinding

    companion object {
        fun newInstance() = ListDetailFragment()
    }

    private lateinit var viewModel: ListDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDetailFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ListDetailViewModel::class.java)
        // TODO: Use the ViewModel


        try {
            (activity as MainActivity?)?.LoadEditText()
        }
        catch (e: ClassCastException) { null }
        finally {

        }
    }
}