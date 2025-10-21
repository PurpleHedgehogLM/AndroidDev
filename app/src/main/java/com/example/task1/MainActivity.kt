package com.example.task1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.task1.ui.theme.Task1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task1Theme {
                QuestionnaireScreen()
            }

        }
    }
}
@Preview(showBackground = true, name = "LightTheme")
@Composable
fun PreviewLight() {
    Task1Theme { QuestionnaireScreen() }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,name = "DarkTheme")
@Composable
fun PreviewDark() {
    Task1Theme { QuestionnaireScreen() }
}

@Composable
fun QuestionnaireScreen() {
    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableFloatStateOf(16f) }
    var isMale by rememberSaveable { mutableStateOf(true) }
    var isChecked by rememberSaveable { mutableStateOf(false) }
    var showSummary by rememberSaveable { mutableStateOf(false) }

    val isFormValid = name.isNotBlank()
    val resetSummary = { showSummary = false } //для того, чтобы сводка внизу пропадала, когда изменяется какое-либо значение в анкете
    val focusManager = LocalFocusManager.current
    Surface(color = MaterialTheme.colorScheme.surface) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus() //скрывается клавиатура при нажатии мимо строки ввода
                    }
                },
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier.padding(top = 40.dp, start = 50.dp, end = 50.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        text = stringResource(id = R.string.user_questionnaire),
                        fontSize = 30.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = stringResource(id = R.string.avatar),
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            stringResource(id = R.string.label_name),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        TextField(
                            value = name,
                            onValueChange = {
                                name = it
                                resetSummary()
                            },
                            placeholder = { Text(stringResource(id = R.string.placeholder_name)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            stringResource(id = R.string.label_age),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Slider(
                                value = age,
                                onValueChange = {
                                    age = it
                                    resetSummary()
                                },
                                valueRange = 16f..99f,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${age.toInt()}",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            stringResource(id = R.string.label_gender),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                16.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isMale,
                                    onClick = {
                                        isMale = true
                                        resetSummary()
                                    }
                                )
                                Text(
                                    stringResource(id = R.string.gender_male),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = !isMale,
                                    onClick = {
                                        isMale = false
                                        resetSummary()
                                    }
                                )
                                Text(
                                    stringResource(id = R.string.gender_female),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 16.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = it
                                resetSummary()
                            }
                        )
                        Text(
                            stringResource(id = R.string.news),
                            modifier = Modifier.padding(start = 2.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                item {
                    Button(
                        onClick = {
                            showSummary = true
                        },
                        enabled = isFormValid,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor =  MaterialTheme.colorScheme.onSurface,
                            disabledContainerColor =  MaterialTheme.colorScheme.surfaceVariant
                        )                    ) {
                        Text(
                            stringResource(id = R.string.button_submit)
                        )
                    }
                }
                if (showSummary) {
                    item {
                        Column(modifier = Modifier.padding(top = 16.dp, bottom = 20.dp)) {
                            Text(stringResource(id = R.string.summary_name, name))
                            Text(stringResource(id = R.string.summary_age, age.toInt()))
                            Text(
                                stringResource(
                                    id = R.string.summary_gender,
                                    if (isMale) stringResource(R.string.gender_male) else stringResource(
                                        R.string.gender_female
                                    )
                                )
                            )
                            Text(
                                stringResource(
                                    id = R.string.summary_sub,
                                    if (isChecked) stringResource(R.string.sub_yes) else stringResource(
                                        R.string.sub_no
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}


