package com.example.assignment3

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.system.exitProcess
import androidx.compose.material.Icon as Icon1
import androidx.compose.material.Text as Text1

@Composable
fun Form1(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        TopSection()
        Card(navController)
    }
}


@Composable
fun TopSection() {
    Box(
        modifier = Modifier
            .background(
                color = Orange,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text1(text = "Details of Inspected Items:", color = White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Card(navController: NavHostController) {

    fun NavHostController.navigateWithPopUp(
        toRoute: String,  // route name where you want to navigate
        fromRoute: String // route you want from popUpTo.
    ) {
        this.navigate(toRoute) {
            popUpTo(fromRoute) {
                inclusive = true // It can be changed to false if you
                // want to keep your fromRoute exclusive
            }
        }
    }

    var showAlertDialog by remember { mutableStateOf(false)}
    BackHandler {
        showAlertDialog = true
    }


        var fullName by remember {
            mutableStateOf("")
        }
        var address by remember {
            mutableStateOf("")
        }
        var date by remember {
            mutableStateOf("")
        }
        var mobileNumber by remember {
            mutableStateOf("")
        }
        var location by remember {
            mutableStateOf("Jaipur")
        }
        var description by remember {
            mutableStateOf("")
        }
        var testFiled by remember {
            mutableStateOf("")
        }

    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val mDate = remember { mutableStateOf("") }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth - ${mMonth + 1} - $mYear"
        }, mYear, mMonth, mDay
    )

    val  jsonArray = JSONArray()


    fun setTheVal(context: Context,key: String?) {
        Log.v("hi","hi------------")
        val jsonObj = JSONObject()

        jsonObj.put("fullName", fullName)
        jsonObj.put("address", address)
        jsonObj.put("date", mDate.value)
        jsonObj.put("mobileNumber", mobileNumber)
        jsonObj.put("location", location)
        jsonObj.put("description", description)
        jsonObj.put("testFiled", testFiled)

        Log.v("hi","hi2------------")
        Log.v("hi","jsonObj $jsonObj")

        jsonArray.put(jsonObj)

        Log.v("arr",jsonArray.toString())
    }

    val context = LocalContext.current

    androidx.compose.material.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = White,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if(showAlertDialog){
                AlertDialog(
                    onDismissRequest = { showAlertDialog=false },
                    title = { Text1(text = "Exit?") },
                    text = { Text1(text = "Are you sure you want to exit?") },
                    confirmButton = {
                        TextButton(onClick = {
                            exitProcess(0)
                        }) {
                            Text1(text = "Yes")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showAlertDialog=false }) {
                            Text1(text = "No")
                        }
                    }
                )
            }
            OutlinedTextField(
                maxLines = 1,
                value = fullName,
                onValueChange = { name ->
                    fullName = name
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text1("Full Name") },

                )
            OutlinedTextField(
                maxLines = 1,
                value = address,
                onValueChange = { value ->
                    address = value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 2.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text1("Address") },

                )
            Box(modifier = Modifier.clickable {
                mDatePickerDialog.show()
            }) {
                OutlinedTextField(
                    maxLines = 1,
                    enabled = false,
                    value = mDate.value,
                    onValueChange = { value: String ->
                        mDate.value=value
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),


                    shape = RoundedCornerShape(8.dp),
                    label = { Text1(text = "Date") },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Black,

                        backgroundColor = White,
                        disabledTextColor = Black,
                    ),

                    )
            }
            OutlinedTextField(
                maxLines = 1,
                value = mobileNumber,
                onValueChange = { value ->
                    if (value.length <= 10) mobileNumber = value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text1("Mobile") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Next,
                    ),
                )

            DropDown(location = { selectedLocation ->
                location=selectedLocation
            })

            OutlinedTextField(
                maxLines = 1,
                value = description,
                onValueChange = { value ->
                    description = value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 2.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text1("Description") },

                )
            OutlinedTextField(
                maxLines=1,
                value = testFiled,
                onValueChange = { value ->
                    testFiled = value
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, top = 2.dp),
                shape = RoundedCornerShape(8.dp),
                label = { Text1("Test Filed") },

                )

        }
    }
    Row(
        modifier= Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = LightOrange,contentColor = White),
            onClick = {
            if (fullName.isNotEmpty() ){
                if (mobileNumber.isNotEmpty()) {
                    if(mobileNumber.length!=10){
                        Toast.makeText(mContext, "phone number must be of 10 digits", Toast.LENGTH_SHORT).show()
                    }else{
                        if (android.util.Patterns.PHONE.matcher(mobileNumber).matches()) {

                            val jsonObj = JSONObject()

                            jsonObj.put("fullName", fullName)
                            jsonObj.put("address", address)
                            jsonObj.put("date", mDate.value)
                            jsonObj.put("mobileNumber", mobileNumber)
                            jsonObj.put("location", location)
                            jsonObj.put("description", description)
                            jsonObj.put("testFiled", testFiled)



                            jsonArray.put(jsonObj)
                            fullName=""
                            address=""
                            date=""
                            mDate.value=""
                            mobileNumber=""
                            location=""
                            description=""
                            location="Jaipur"
                            testFiled=""
                            Toast.makeText(mContext, "Form data saved", Toast.LENGTH_SHORT).show()
//                        navController.navigateWithPopUp(Screen.Form2.route,Screen.Form1.route)

                        } else {
                            Toast.makeText(mContext, "Phone Number is invalid..", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    Toast.makeText(mContext, "Please enter phone number", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(mContext, "Please enter full name", Toast.LENGTH_SHORT).show()
            }


        }) {
            Text1("Save")
        }
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = LightOrange,
                contentColor = White),
            onClick = {
                if(jsonArray.length()!=0){
                    val jsonString = jsonArray.toString()
                    Log.d("args",jsonString)
                    navController.navigate(route = "form2/$jsonString")
                }else{
                    Toast.makeText(mContext, "Please fill form first and save", Toast.LENGTH_SHORT).show()
                }

            }) {
            Text1("Next")
        }

    }
}

@Composable
fun DropDown(location: (String)-> Unit){
    val options = listOf("Jaipur", "Ajmer", "Mumbai", "Goa", "Delhi")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }
    val icon = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown
    val focusManager = LocalFocusManager.current

    Column {
        OutlinedTextField(
            value = selectedOption,
            readOnly = true,
            onValueChange = {},
            trailingIcon = {
                Icon1(icon, null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    expanded = it.isFocused
                }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
                focusManager.clearFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            options.forEach { label ->
                DropdownMenuItem(onClick = {
                    selectedOption = label
                    location(selectedOption)
                    expanded = false
                    focusManager.clearFocus()
                }) {
                    Text1(text = label)
                }
            }
        }
    }
}




