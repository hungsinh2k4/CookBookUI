package com.example.androidcookbook.ui.screen

enum class CookbookScreens(
    val hasTopBar: Boolean = true,
    val hasBottomBar: Boolean = true
) {
    Login(hasTopBar = false, hasBottomBar = false),
    Category,
    AIChat,
    Newsfeed,
    UserProfile,
    Search(hasBottomBar = false),
    CreatePost(hasBottomBar = false),
}