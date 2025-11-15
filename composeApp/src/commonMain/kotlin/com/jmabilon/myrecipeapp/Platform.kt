package com.jmabilon.myrecipeapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform