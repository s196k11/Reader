package com.example.reader.screens.login


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reader.components.EmailInput
import com.example.reader.components.PasswordInput
import com.example.reader.components.ReaderLogo
import com.example.reader.navigation.ReaderScreens
import com.google.protobuf.MapEntryLite

@Composable
fun ReaderLoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
){
    val context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            UserForm(
                bText = "Login",
                startT = "Create Account?",
                eText = " SignUp",
                onClick = {navController.navigate(ReaderScreens.CreateAccountScreen.name)},
            ){email,password ->
                viewModel.signInWithEmailAndPassword(email, password){
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    Toast.makeText(context,"Logged In",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}



@Composable
fun UserForm(
    bText:String,
    startT:String,
    eText:String,
    onClick: ()->Unit,
    onDone : (String,String) ->Unit
){
    val email = rememberSaveable { mutableStateOf("")}
    val password = rememberSaveable { mutableStateOf("")}
    val passwordVisibility = rememberSaveable {mutableStateOf(false)}
    val passwordFocusRequest = FocusRequester.Default

    val valid = remember(email, password) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val focusManager = LocalFocusManager.current
    val modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = email.value,
            onValueChange = {email.value = it},
            label = {Text(text = "Email")},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next, keyboardType = KeyboardType.Email),
            keyboardActions = KeyboardActions(onNext = {focusManager.moveFocus(focusDirection = FocusDirection.Down)}),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground)
        )

        OutlinedTextField(
            value = password.value,
            onValueChange = {password.value = it},
            label = {Text(text = "Password")},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Password),
            keyboardActions = KeyboardActions(onDone = {onDone(email.value.trim(),password.value.trim())}),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
            textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
            visualTransformation = PasswordVisualTransformation()
        )

        Button(onClick = { onDone(email.value.trim(),password.value.trim()) },modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(10.dp), shape = CircleShape) {
            Text(text = bText)
        }

        Row {
            Text(text = startT)
            Text(text = eText,color = Color.Red.copy(alpha = 0.8f),modifier = Modifier.clickable { onClick() })
        }
    }
}

//@Composable
//fun SubmitButton(
//    textId: String,
//    loading: Boolean,
//    validInputs: Boolean,
//    onClick:() -> Unit
//) {
//    Button(onClick = onClick,modifier = Modifier
//        .padding(3.dp)
//        .fillMaxWidth(), enabled = !loading && validInputs, shape = CircleShape){
//        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
//        else Text(text = textId,modifier = Modifier.padding(5.dp))
//    }
//}











