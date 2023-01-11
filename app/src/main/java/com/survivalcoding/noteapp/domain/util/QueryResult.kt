package com.survivalcoding.noteapp.domain.util

sealed class QueryResult {
    class Success<T>(val value: T) : QueryResult()
    class Fail(val msg: String) : QueryResult()
}
