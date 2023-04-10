package com.example.mad_learningdiary2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mad_learningdiary2.navigation.MyNavigation
import com.example.mad_learningdiary2.ui.theme.MovieAppMAD23Theme


class MainActivity : ComponentActivity()   {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD23Theme {
                MyNavigation()
            }
        }
    }
    override fun onStart() {
        super.onStart()
        Log.i("MainActivity", "onStart called")
    }
}