package com.example.DataClasses.Event

enum class StatusEvent(val status : String){
    CREATE("Создано"),
    ISGONE("Проведено"),
    DELETE("Отменено")
}
