package io.android.movies.auth.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.android.movies.R

@Composable
fun AuthScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.auth_title),
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = email,
            label = {
                Text(
                    text = stringResource(id = R.string.auth_email)
                )
            },
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = password,
            label = {
                Text(
                    text = stringResource(id = R.string.auth_password)
                )
            },
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.weight(1f))
        FilledTonalButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.auth_login)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.auth_registration)
            )
        }
    }
}

@Preview
@Composable
fun AuthScreen_Preview() {
    AuthScreen()
}