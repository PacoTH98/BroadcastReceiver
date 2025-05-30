package com.example.broadcastreceiver

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {

    private val permissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (!allGranted) {
            Log.e("MainActivity", "Permisos no concedidos")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestPermissions()

        setContent {
            AutoReplyScreen()
        }
    }

    private fun requestPermissions() {
        permissionRequest.launch(
            arrayOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.SEND_SMS
            )
        )
    }
}

@Composable
fun AutoReplyScreen() {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf(TextFieldValue(SharedPrefManager.getSavedNumber(context))) }
    var message by remember { mutableStateOf(TextFieldValue(SharedPrefManager.getSavedMessage(context))) }

    val primaryColor = Color(0xFF00695C) // Verde petróleo
    val accentColor = Color(0xFFB2DFDB)  // Verde suave
    val textColor = Color(0xFF212121)    // Gris oscuro

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFF1F8F9) // Fondo azulado suave
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Respuesta Automática",
                fontSize = 26.sp,
                color = primaryColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Número de teléfono", color = textColor) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = accentColor
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Mensaje de respuesta", color = textColor) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = accentColor
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val num = phoneNumber.text.trim()
                    val msg = message.text.trim()

                    if (num.isNotEmpty() && msg.isNotEmpty()) {
                        SharedPrefManager.saveNumber(context, num)
                        SharedPrefManager.saveMessage(context, msg)
                        Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Completa ambos campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(
                    text = "Guardar configuración",
                    color = Color.White,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }
    }
}
