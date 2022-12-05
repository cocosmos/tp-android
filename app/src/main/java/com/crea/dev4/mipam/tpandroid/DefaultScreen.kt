package com.crea.dev4.mipam.tpandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


    @Composable
    fun BoardingScreen(
        onDismiss: () -> Unit,
        shouldGetFormData: MainActivity.Answer,
    ) {

        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text("Bienvenue sur l'app de Mipam!", style = MaterialTheme.typography.h4,textAlign = TextAlign.Center,)

                Button(
                    modifier = Modifier.padding(vertical = 24.dp),
                    onClick = onDismiss
                ) {
                    Text("Remplir formulaire")
                }
                if(shouldGetFormData.name != ""){
                    FormAnswer(answers = shouldGetFormData)
                }

            }
        }
    }

    @Composable
    fun FormAnswer (answers: MainActivity.Answer) {
        Text("Réponses du formulaire:", style = MaterialTheme.typography.h6, modifier = Modifier.padding(bottom = 16.dp))
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Nom: ${answers.name}")
            Text("Android: ${answers.happy}")
            Text("Latitude: ${answers.latitude}")
            Text("Longitude: ${answers.longitude}")

        }
    }






