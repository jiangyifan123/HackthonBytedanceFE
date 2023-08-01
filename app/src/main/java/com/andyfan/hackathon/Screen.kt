package com.andyfan.hackathon

sealed class Screen(val router: String) {
    object Home: Screen(router = "home_screen")
    object Demo: Screen(router = "demo_screen")
    object Preview: Screen(router = "preview_screen")
}
