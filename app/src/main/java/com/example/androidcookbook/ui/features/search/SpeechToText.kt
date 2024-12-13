package com.example.androidcookbook.ui.features.search

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class SpeechRecognizerManager(
    context: Context,
    private val onResult: (String) -> Unit,
    private val onError: (String) -> Unit
) {
    private val speechRecognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    //Tạo yêu cầu (dự định) để gửi tới hệ thống để xử lí giọng nói
    private val recognizerIntent: Intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        //Chỉ định kiểu nhận diện giọng nói: Free (tự do), không phuj thuộc vào cú pháp hay ngữ cảnh
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        //Nhận diện ngôn ngữ: ngôn ngữ mặc định của thiết bị
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        //Nhaanj kết quả trung gian trước khi có kết quả cuôi cùng
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak Something")
    }

    init {
        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                onError("Listening...")
            }

            override fun onBeginningOfSpeech() {
                onError("Recording...")
            }

            override fun onRmsChanged(rmsdB: Float) {}

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                onError("Processing...")
            }

            override fun onError(error: Int) {
                onError("Error occurred: $error")
            }

            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val text = matches?.get(0) ?: "No speech recognized"
                onResult(text)
            }

            override fun onPartialResults(partialResults: Bundle?) {}

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    fun startListening() {
        speechRecognizer.startListening(recognizerIntent)
    }

    fun stopListening() {
        speechRecognizer.stopListening()
    }

    fun destroy() {
        speechRecognizer.destroy()
    }
}

class SpeechToTextViewModel(application: Application) : AndroidViewModel(application) {
    private val _speechText = MutableStateFlow("")
    val speechText: StateFlow<String> get() = _speechText

    private var speechRecognizerManager: SpeechRecognizerManager? = null

    fun initializeSpeechRecognizer(context: Context) {
        speechRecognizerManager = SpeechRecognizerManager(
            context = context,
            onResult = { result ->
//                run {
//                    _speechText.value = result
//                    Log.d("SPEECH", "initializeSpeechRecognizer: ${result}")
//                }
            },
            onError = { error ->
//                run {
//                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
//                    Log.d("SPEECH", "initializeSpeechRecognizer: ${error}")
//                }
            }
        )
    }

    fun startListening() {
        speechRecognizerManager?.startListening()
    }

    fun stopListening() {
        speechRecognizerManager?.stopListening()
    }

    override fun onCleared() {
        super.onCleared()
        speechRecognizerManager?.destroy()
    }

    fun changeText(str: String) {
        _speechText.update { str }
    }
}

@Composable
fun SpeechToTextScreen(viewModel: SpeechToTextViewModel) {
    var isPermissionGranted by remember { mutableStateOf(false) }

    if (!isPermissionGranted) {
        RequestAudioPermission {
            isPermissionGranted = true
        }
    } else {
        Text(text = "Permission Granted! Ready to use Speech-to-Text.")
        val context = LocalContext.current
        val speechText by viewModel.speechText.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.initializeSpeechRecognizer(context)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Speech to Text Example",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = { viewModel.startListening() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Mic",
                    tint = Color.Green,
                    modifier = Modifier
                        .height(100.dp)
                        .width(100.dp)
                        .padding(5.dp)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = speechText,
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center
            )
        }
    }

}

//Gửi yêu cầu sử dụng mic
@Composable
fun RequestAudioPermission(onPermissionGranted: () -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(android.Manifest.permission.RECORD_AUDIO)
    }
}
