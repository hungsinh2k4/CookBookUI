package com.example.androidcookbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androidcookbook.ui.component.CookbookAppBar
import com.example.androidcookbook.ui.CookbookApp
import com.example.androidcookbook.ui.screen.signinandup.SignPreview
import com.example.androidcookbook.ui.theme.AndroidCookbookTheme
import com.example.androidcookbook.ui.uistate.SignUiState
import com.example.androidcookbook.ui.viewmodel.SignViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidCookbookTheme {
//                CookbookApp()
                val viewModel: SignViewModel = viewModel()
                val signState: SignUiState by viewModel.uiState.collectAsState()
                if (!signState.signedIn) {
                    SignPreview()
                } else {
                    CookbookApp()
                }

            }
        }
    }
}
