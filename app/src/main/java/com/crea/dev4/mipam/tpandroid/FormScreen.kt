package com.crea.dev4.mipam.tpandroid

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun FormScreen(onDismiss: () -> Unit, shouldGetFormData: MainActivity.Answer, tvLatitude: String, tvLongitude: String, getCurrentLocation: () -> Unit) {
    val radioOptions = listOf("J'adore","J'aime bien", "Ni chaud ni froid","J'aime pas", "Je déteste")

    val (name, setName) = rememberSaveable { mutableStateOf("") }
    val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf(radioOptions[0]) }

    fun onContinueClicked() {
        shouldGetFormData.name = name
        shouldGetFormData.happy = selectedOption
        shouldGetFormData.latitude = tvLatitude
        shouldGetFormData.longitude = tvLongitude
        onDismiss()
    }


    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text("Formulaire à remplir !", style = MaterialTheme.typography.h5, modifier = Modifier.padding(bottom = 32.dp))
            TextField(
                value = name,
                onValueChange = { setName(it) },
                label = { Text("Votre nom") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)

            )
            Text("Es ce que Android c'est bien ?")
            Column {
                for (radioOption in radioOptions) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(radioOption == selectedOption, onClick = {
                                onOptionSelected(radioOption)
                            })
                    ) {
                        RadioButton(selected = radioOption == selectedOption, onClick = {
                            onOptionSelected(radioOption)
                        })
                        Text(text = radioOption, modifier = Modifier.padding(top = 13.dp))
                    }
                }

            }
            Row (modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.padding(20.dp)) {
                    Text(text = "Latitude : $tvLatitude")
                    Text(text = "Longitude : $tvLongitude")
                }
                Button(onClick = { getCurrentLocation() }) {
                    Text(text = "Localisation")
                }


            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                onClick = { onContinueClicked() }
            ) {
                Text("Valider")
            }
        }
    }

}