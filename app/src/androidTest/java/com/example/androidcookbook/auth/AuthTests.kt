package com.example.androidcookbook.auth

import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.example.androidcookbook.MainActivity
import com.example.androidcookbook.auth.AuthTestConst.PASSWORD
import com.example.androidcookbook.auth.AuthTestConst.SIGN_IN_SUCCESS
import com.example.androidcookbook.auth.AuthTestConst.TOKEN
import com.example.androidcookbook.auth.AuthTestConst.USERNAME
import com.example.androidcookbook.auth.AuthTestConst.USER_ID
import com.example.androidcookbook.data.network.AuthService
import com.example.androidcookbook.domain.model.auth.SignInRequest
import com.example.androidcookbook.domain.model.auth.SignInResponse
import com.example.androidcookbook.domain.model.user.User
import com.example.androidcookbook.ui.CookbookUiState
import com.example.androidcookbook.ui.CookbookViewModel
import com.example.androidcookbook.ui.features.auth.AuthRequestState
import com.example.androidcookbook.ui.features.auth.AuthViewModel
import com.example.androidcookbook.ui.features.auth.screens.LoginScreen
import com.example.androidcookbook.ui.features.auth.screens.PASSWORD_TEXT_FIELD_TEST_TAG
import com.example.androidcookbook.ui.features.auth.screens.USERNAME_TEXT_FIELD_TEST_TAG
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
class AuthTests {
    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var authService: AuthService

    @Before
    fun setup() {
        hiltTestRule.inject()
        composeTestRule.activity.setContent {
            val authViewModel = composeTestRule.activity.viewModels<AuthViewModel>().value
            LoginScreen(
                onForgotPasswordClick = {},
                onNavigateToSignUp = {},
                onSignInClick = { username, password ->
                    authViewModel.signIn(username, password) {

                    }
                },
                onUseAsGuest = {},
                requestState = authViewModel.authRequestState.collectAsState().value
            )
        }
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.androidcookbook", appContext.packageName)
    }

    @Test
    fun testLoginSuccess() {
        // Mock behavior for a successful login
        coEvery { authService.login(SignInRequest(USERNAME, PASSWORD)) } returns
                ApiResponse.Success(SignInResponse(TOKEN, SIGN_IN_SUCCESS, User(USER_ID, name = USERNAME)))

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag(USERNAME_TEXT_FIELD_TEST_TAG).performTextInput(USERNAME)
        composeTestRule.onNodeWithTag(PASSWORD_TEXT_FIELD_TEST_TAG).performTextInput(PASSWORD)
        composeTestRule.onNodeWithText("Sign In").performClick()

        // Add assertions
        val authViewModel = composeTestRule.activity.viewModels<AuthViewModel>().value
        val uiState = authViewModel.uiState.value
        assertTrue(uiState.signInSuccess)
    }
}