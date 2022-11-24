package com.crea.dev4.mipam.tpandroid

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.crea.dev4.mipam.tpandroid.ui.theme.TPAndroidTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TPAndroidTheme {
                MyApp()
            }
        }
    }
}


data class Answer(var name: String, var happy: String)

val AnswerSaver = run {
    val nameKey = "Name"
    val happyKey = "Happy"
    mapSaver(
        save = { mapOf(nameKey to it.name, happyKey to it.happy) },
        restore = { Answer(it[nameKey] as String, it[happyKey] as String) }
    )
}

@Composable
private fun MyApp() {
    var shouldShowBoarding by rememberSaveable { mutableStateOf(true) }
    val shouldGetFormData by rememberSaveable(stateSaver = AnswerSaver) {
        mutableStateOf(
            Answer(
                "",
                ""
            )
        )
    }

    if (shouldShowBoarding) {
        BoardingScreen(
            onDismiss = { shouldShowBoarding = false }, shouldGetFormData = shouldGetFormData,
        )
    } else {
        FormScreen(
            onDismiss = { shouldShowBoarding = true },
            shouldGetFormData = shouldGetFormData
        )
    }
}

@Composable
fun BoardingScreen(
    onDismiss: () -> Unit,
    shouldGetFormData: Answer,
) {

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bienvenue sur MipamApp!", style = MaterialTheme.typography.h5)
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onDismiss
            ) {
                Text("Remplir formulaire")
            }

            ShowAnswers(shouldGetFormData)
        }
    }
}

@Composable
fun ShowAnswers(answers: Answer) {

    Column {
        Text("Réponse formulaire:", style = MaterialTheme.typography.h6)
        Text("Nom: ${answers.name}")
        Text("Heureux: ${answers.happy}")
    }

}


@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 320,
    name = "DefaultPreview",
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun DefaultPreview() {
    TPAndroidTheme {
        MyApp()
    }
}


@Composable
private fun FormScreen(onDismiss: () -> Unit, shouldGetFormData: Answer) {
    val radioOptions = listOf("Oui", "Peut-être", "Non")

    val (name, setName) = rememberSaveable { mutableStateOf("") }
    val (selectedOption, onOptionSelected) = rememberSaveable { mutableStateOf(radioOptions[0]) }

    fun onContinueClicked() {
        shouldGetFormData.name = name
        shouldGetFormData.happy = selectedOption
        onDismiss()
    }


    Surface {
        Column(
            modifier = Modifier
                .padding(16.dp),

            ) {
            TextField(
                value = name,
                onValueChange = { setName(it) },
                label = { Text("Votre nom") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)

            )
            Text("Es-tu heureux ?")
            Column {
                for (radioOption in radioOptions) {
                    Row(modifier = Modifier
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


//@Preview(showBackground = true, widthDp = 360, heightDp = 640,
//    name = "DefaultPreview")
//@Composable
//private fun DefaultPreview() {
//    TPAndroidTheme {
//        Form()
//    }
//}
//
//
//
//@Preview(showBackground = true, widthDp = 360, heightDp = 640,uiMode = UI_MODE_NIGHT_YES,
//    name = "DefaultPreviewDark")
//@Composable
//private fun DefaultPreviewDark() {
//    TPAndroidTheme {
//      Form()
//    }
//}