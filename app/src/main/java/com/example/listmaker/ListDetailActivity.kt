package com.example.listmaker

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.listmaker.models.TaskList
import com.example.listmaker.ui.detail.ListDetailFragment
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.EditText


class ListDetailActivity : AppCompatActivity() {
    lateinit var list: TaskList
    lateinit var listDetailEdittext: EditText
    lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_detail_activity)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title = list.name

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListDetailFragment.newInstance())
                .commitNow()
        }
    }

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        sharedPreference = getSharedPreferences("", MODE_PRIVATE)
        listDetailEdittext = findViewById(R.id.list_detail_edittext)
        var loadText = sharedPreference.getString(list.name,"")
        listDetailEdittext.setText(loadText)
        super.onPostCreate(savedInstanceState)
    }

    public fun UpdateText() {
        sharedPreference = getSharedPreferences("", MODE_PRIVATE)
        listDetailEdittext = findViewById(R.id.list_detail_edittext)
        var loadText = sharedPreference.getString(list.name,"")
        listDetailEdittext.setText(loadText)

    }

    override fun onBackPressed() {
        sharedPreference = getSharedPreferences("", MODE_PRIVATE)
        listDetailEdittext = findViewById(R.id.list_detail_edittext)

        var edited = listDetailEdittext.text.toString()

        listDetailEdittext.setText(edited)
        sharedPreference.edit().putString(list.name,edited).apply()
        super.onBackPressed()
    }
}