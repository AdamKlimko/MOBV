package com.example.zadanie.helpers

enum class SortMethod {
    NAME,
    VISITORS,
    DISTANCE
}

class Sort(
    var sortMethod: SortMethod,
    var asc: Boolean
)