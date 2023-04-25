package com.example.assignment3

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.navigation.NavArgs
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import kotlin.system.exitProcess

@SuppressLint("RememberReturnType")
@Composable
fun Form2(navController: NavHostController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Users Information", color = Color.White) },
                backgroundColor = Color.Blue,
            )
        }

    ) {
        var showAlertDialog by remember { mutableStateOf(false)}
        BackHandler {
            showAlertDialog = true
        }
        if(showAlertDialog){
            AlertDialog(
                onDismissRequest = { showAlertDialog=false },
                title = { Text(text = "Exit?") },
                text = { Text(text = "Are you sure you want to exit?") },
                confirmButton = {
                    TextButton(onClick = {
                        exitProcess(0)
                    }) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAlertDialog=false }) {
                        Text(text = "No")
                    }
                }
            )
        }

        val jsonString = navController.currentBackStackEntry?.arguments?.getString("jsonArray")

        TableScreen(jsonString= jsonString)
    }

}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    lines: Int = 1,
    textColor: Color = Color.Black
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
            .height(20.dp),
        overflow = TextOverflow.Ellipsis,
        color = textColor

    )
}

@Composable
fun TableScreen(jsonString : String?) {

    val jsonArray = JSONArray(jsonString)

    var ind by remember {
        mutableStateOf(0)
    }

    var jsonObj by remember {
        mutableStateOf(jsonArray.getJSONObject(ind))
    }

    var firstName: String? = jsonObj.optString("fullName")
    var address: String? = jsonObj.optString("address")
    var date: String? = jsonObj.optString("date")
    var mobile: String? = jsonObj.optString("mobileNumber")
    var location: String? = jsonObj.optString("location")
    var description: String? = jsonObj.optString("description")
    var test: String? = jsonObj.optString("testFiled")

    val context = LocalContext.current
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        // Here is the header
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Fields", weight = column1Weight, textColor = Color.White)
                TableCell(text = "Value", weight = column2Weight,textColor = Color.White)
            }
        }
        // Here are all the lines of your table.
        if(firstName!=null){
            item {
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = "Full Name", weight = column1Weight)
                    TableCell(text = firstName!!, weight = column2Weight)
                }
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = "Address", weight = column1Weight)
                    TableCell(text = address!!, weight = column2Weight)
                }
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = "Date", weight = column1Weight)
                    TableCell(text = date!!, weight = column2Weight)
                }
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = "Mobile", weight = column1Weight)
                    TableCell(text = mobile!!, weight = column2Weight)
                }
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = "Location", weight = column1Weight)
                    TableCell(text = location!!, weight = column2Weight)
                }
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = "Description", weight = column1Weight)
                    TableCell(text = description!!, weight = column2Weight)
                }
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = "Test Filed", weight = column1Weight)
                    TableCell(text = test!!, weight = column2Weight)
                }
            }

        }else{
            item {
               Text(text = "Please first fill the form")
            }
        }

        item {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightOrange,
                        contentColor = White),
                    onClick = {
                        if(ind>0){
                            ind--
                            jsonObj = jsonArray.getJSONObject(ind)
                        }
                    }) {
                    Text("Prev")
                }
                Text(text = "Page : "+(ind+1).toString(), modifier = Modifier.padding(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = LightOrange,
                        contentColor = White),
                    onClick = {
                        if(ind<jsonArray.length()-1){
                            ind++
                            jsonObj = jsonArray.getJSONObject(ind)
                        }
                    }) {
                    Text("Next")
                }
            }
        }
    }
}