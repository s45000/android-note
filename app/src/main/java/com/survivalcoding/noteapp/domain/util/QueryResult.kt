package com.survivalcoding.noteapp.domain.util

sealed class QueryResult(val msg: String) {
    class Success(msg: String) : QueryResult(msg)
    class Fail(msg: String) : QueryResult(msg)
}
