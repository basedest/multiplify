package com.basedest.multiplify

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.random.Random

class SelectNumberExerciseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SelectNumberExerciseScreen()
        }
    }
}

@Composable
fun SelectNumberExerciseScreen() {
    var selectedNumber by remember { mutableStateOf("") }
    var firstNumber by remember { mutableStateOf(0) }
    var questionIndex by remember { mutableStateOf(1) }
    var secondNumber by remember { mutableStateOf(Random.nextInt(2, 10)) }
    var answer by remember { mutableStateOf("") }
    var correctAnswers by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (firstNumber == 0) {
                    OutlinedTextField(
                        value = selectedNumber,
                        onValueChange = { selectedNumber = it },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        label = { Text("Введите число от 2 до 9") }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = {
                        val number = selectedNumber.toIntOrNull()
                        if (number in 2..9) {
                            firstNumber = number!!
                        } else {
                            // Показываем Snackbar при неверном вводе
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Неверное число. Введите число от 2 до 9.",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }) {
                        Text("Начать упражнение")
                    }
                } else {
                    if (questionIndex <= 20) {
                        Text("Пример $questionIndex: $firstNumber * $secondNumber = ?")

                        OutlinedTextField(
                            value = answer,
                            onValueChange = { answer = it },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            label = { Text("Ваш ответ") }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = {
                            val correctAnswer = firstNumber * secondNumber
                            if (answer.toIntOrNull() == correctAnswer) {
                                correctAnswers++
                                message = "Правильный ответ!"
                            } else {
                                message = "Неверный ответ"
                            }

                            if (questionIndex <= 20) {
                                secondNumber = Random.nextInt(2, 10)
                                questionIndex++
                                answer = ""
                            }
                        }) {
                            Text("Проверить")
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(message)
                    } else {
                        Text("Вы завершили тест! Правильных ответов: $correctAnswers из 20.")
                        Text("Процент правильных ответов: ${(correctAnswers / 20.0 * 100).toInt()}%")
                    }
                }
            }
        }
    )
}
