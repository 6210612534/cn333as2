package com.example.listmaker

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.listmaker.databinding.MainActivityBinding
import com.example.listmaker.models.TaskList
import com.example.listmaker.ui.detail.ListDetailFragment
import com.example.listmaker.ui.main.MainFragment
import com.example.listmaker.ui.main.MainViewModel
import com.example.listmaker.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity(), MainFragment.MainFragmentInteractionListener {
    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    lateinit var listDetailEdittext: EditText
    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this,
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        ).get(MainViewModel::class.java)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {

            val mainFragment = MainFragment.newInstance(this)
            val mainFragmentId : Int = if (binding.xlListDetailFragment == null) {
                R.id.container }
            else {
                R.id.xl_main_activity}

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(mainFragmentId,mainFragment)
            }
        }
        binding.taskListAddButton.setOnClickListener{
            showCreateListDialog()
        }
    }

    private fun showCreateListDialog(){
        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle){ dialog, _ ->
            dialog.dismiss()
            val taskList = TaskList(listTitleEditText.text.toString())
            viewModel.saveList(taskList)
            showListDetail(taskList)
        }
        builder.create().show()
    }

    private fun showListDetail(list: TaskList){
        if (binding.xlListDetailFragment == null) {
            val listDetailIntent = Intent(this,ListDetailActivity::class.java)
            listDetailIntent.putExtra(INTENT_LIST_KEY ,list)
            startActivity(listDetailIntent)

        } else {
            title = list.name
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.xl_list_detail_fragment, ListDetailFragment.newInstance())
            }
        }
    }

    companion object{
        const val INTENT_LIST_KEY = "list"
        var LIST_NAME = "ListMaker"
    }

    override fun listItemTapped(list: TaskList) {
        LIST_NAME = list.name
        showListDetail(list)
    }


    fun LoadEditText() {
        if (binding.xlListDetailFragment != null){
            sharedPreference = getSharedPreferences("", MODE_PRIVATE)
            listDetailEdittext = findViewById(R.id.list_detail_edittext)
            var loadText = sharedPreference.getString(LIST_NAME,"")
            listDetailEdittext.setText(loadText)
        }
    }

    override fun onBackPressed() {
        if (binding.xlListDetailFragment != null){
            sharedPreference = getSharedPreferences("", MODE_PRIVATE)
            listDetailEdittext = findViewById(R.id.list_detail_edittext)

            var edited = listDetailEdittext.text.toString()
            sharedPreference.edit().putString(LIST_NAME,edited).apply()


            title = resources.getString(R.string.app_name)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                remove(supportFragmentManager.findFragmentById(R.id.xl_list_detail_fragment)!!)
            }
        }
        else{super.onBackPressed()}
    }
}