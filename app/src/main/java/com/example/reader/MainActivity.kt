package com.example.reader

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reader.navigation.ReaderNavigation
import com.example.reader.ui.theme.ReaderTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import org.intellij.lang.annotations.JdkConstants


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReaderTheme {
//                val db = Firebase.firestore
//                val user = hashMapOf(
//                    "first" to "Saif",
//                    "last" to "Saif ",
//                    "born" to 2004
//                )
//
//                db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener {
//                        Log.d("FireStore","msg : ${it.id}")
//                    }.addOnFailureListener {
//                        Log.d("FireStore","msg : ${it}")
//                    }


                ReaderApp()

            }
        }
    }
}


@Composable
fun ReaderApp(){
    Surface(color = MaterialTheme.colors.background,modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderNavigation()
        }

    }
}