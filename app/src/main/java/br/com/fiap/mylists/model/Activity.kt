package br.com.fiap.mylists.model

data class Activity(
    var id: String = "",
    var title: String = "",
    var subject: String = "",
    var deadline: String = "",
    var done: Boolean = false
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
