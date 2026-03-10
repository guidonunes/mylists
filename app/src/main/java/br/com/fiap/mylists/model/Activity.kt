package br.com.fiap.mylists.model

data class Activity(
    var id: String ="",
    val title: String = "",
    val subject: String = "",
    val deadline: String="",
    val done: Boolean = false
) {
    fun toJson(): Map<String, Any>  =
        mapOf(
            "id" to id,
            "title" to title,
            "subject" to subject,
            "deadline" to deadline,
            "done" to done
        )
}
