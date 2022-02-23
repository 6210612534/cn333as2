package com.example.listmaker.ui.main

import android.content.SharedPreferences
import android.widget.EditText
import androidx.lifecycle.ViewModel
import com.example.listmaker.models.TaskList
import org.w3c.dom.Text

class MainViewModel(private val sharedPreference: SharedPreferences) : ViewModel() {
    lateinit var onListAdded: (() -> Unit)

    val lists: MutableList<TaskList> by lazy {
        retrieveLists()
    }

    private fun retrieveLists(): MutableList<TaskList> {
        val sharedPreferenceContents = sharedPreference.all
        val taskLists = ArrayList<TaskList>()

        for (taskList in sharedPreferenceContents){
            val itemHashSet = ArrayList(taskList.value as HashSet<String>)
            val list = TaskList(taskList.key,itemHashSet)
            taskLists.add(list)
        }
        return taskLists
    }

    fun saveList(list: TaskList){
        sharedPreference.edit().putStringSet(list.name,list.task.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()
    }
}