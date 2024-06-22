    package com.fesaragon.facebook.ui

    import android.content.Context
    import android.content.Intent
    import android.net.Uri
    import android.util.Log
    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.text.KeyboardActions
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Email
    import androidx.compose.material.icons.filled.Info
    import androidx.compose.material.icons.filled.Lock
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.platform.LocalFocusManager
    import androidx.compose.ui.text.input.ImeAction
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.compose.ui.text.style.TextDecoration
    import androidx.compose.ui.text.TextStyle
    import androidx.compose.ui.text.font.FontWeight
    import kotlinx.coroutines.launch
    import com.fesaragon.facebook.ui.theme.FacebookTheme

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable

    fun FormScreen() {
        val TAG: String = "FormScreen"
        val email = remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current
        val maxLengthEmail: Int = 50
        val contraseña = remember { mutableStateOf("") }
        val maxLenghtContraseña: Int = 40
        val snackBarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        val context = LocalContext.current

        Scaffold (
            modifier = Modifier.fillMaxSize(),

            floatingActionButton = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 45.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                ) {
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                val result = snackBarHostState.showSnackbar(
                                    "Si tienes dudas por favor comunícate con soporte tecnico",
                                    duration = SnackbarDuration.Long,
                                    actionLabel = "Llamar"
                                )
                                when(result) {
                                    SnackbarResult.Dismissed -> Log.d(TAG, "Se cerró el snackbar")
                                    SnackbarResult.ActionPerformed -> openPhone(context)
                                }
                            }
                        },
                        containerColor = Color(red = 0, green = 90, blue = 164),
                    ) {
                        Icon(Icons.Filled.Info, contentDescription = "Icono de ayuda", tint = Color.White)
                    }
                }
            },
            snackbarHost = {
                SnackbarHost(
                    hostState = snackBarHostState,
                    snackbar = {
                        Snackbar(
                            snackbarData = it,
                            containerColor = Color.White,
                            contentColor = Color.Black,
                            actionColor = Color.Blue
                        )
                    }
                )
            }
        )

        { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .background(Color(red = 9, green = 99, blue = 213))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            )

            {
                Text(
                    text = "Facebook",
                    color = Color.White,
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(85.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(60.dp))

                //correo
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .background(Color.White),

                    value = email.value,
                    label = { Text(text = "Email", color = Color.Gray) },

                    onValueChange = { text -> if (text.length <= maxLengthEmail) { email.value = text }},

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Icono de Correo", tint = Color.DarkGray) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions =  KeyboardActions(onDone ={ focusManager.clearFocus()}),

                    )
                Spacer(modifier = Modifier.height(20.dp))

                //contraseña
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                        .background(Color.White),

                    value = contraseña.value,
                    label = { Text(text = "Password", color = Color.Gray) },

                    onValueChange = { text -> if (text.length <= maxLenghtContraseña) { contraseña.value = text }},

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Icono de contraseña", tint = Color.DarkGray) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions =  KeyboardActions(onDone ={ focusManager.clearFocus()}),
                )
                Button(onClick =
                { println("Log In")
                    Log.d(TAG, "Email: ${email.value}")
                    Log.d(TAG, "Password: ${contraseña.value}")
                    Log.d("FormScreen", "Log In")
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 0, green = 72, blue = 131),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()

                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(fraction = 0.85f),
                        text = "Log In",
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(60.dp))

                Text(
                    text = "Sign Up for Facebook",
                    color = Color.White,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    textAlign =  TextAlign.Center,
                    style = TextStyle(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }

    fun openPhone(context: Context) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:(+1) 650 543 4800"))
        context.startActivity(intent)
    }

    @Preview
    @Composable
    fun FormScreenPreview() {
        FacebookTheme {
            FormScreen()
        }
    }