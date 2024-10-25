package com.basedest.multiplify

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
import kotlin.random.Random

class AllNumbersExerciseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExerciseScreen()
        }
    }
}

@Composable
fun ExerciseScreen() {
    var questionIndex by remember { mutableStateOf(1) }
    var number1 by remember { mutableStateOf(Random.nextInt(2, 10)) }
    var number2 by remember { mutableStateOf(Random.nextInt(2, 10)) }
    var answer by remember { mutableStateOf("") }
    var correctAnswers by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (questionIndex <= 20) {
            Text("Пример $questionIndex: $number1 * $number2 = ?")

            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                label = { Text("Ваш ответ") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                val correctAnswer = number1 * number2
                if (answer.toIntOrNull() == correctAnswer) {
                    correctAnswers++
                    message = "Правильный ответ!"
                } else {
                    message = "Неверный ответ"
                }

                if (questionIndex <= 20) {
                    number1 = Random.nextInt(2, 10)
                    number2 = Random.nextInt(2, 10)
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
