package br.com.fiap.mylists.model

data class Activity(
    val id: String,
    val title: String = "",
    val subject: String = "",
    val deadline: String,
    val done: Boolean = false
)
