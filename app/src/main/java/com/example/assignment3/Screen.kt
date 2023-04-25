package com.example.assignment3

sealed class Screen(val route: String){
    object Home : Screen("home")
    object Form1 : Screen("form1")
    object Form2 : Screen("form2/{jsonArray}")
}