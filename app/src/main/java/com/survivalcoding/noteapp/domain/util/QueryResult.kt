package com.survivalcoding.noteapp.domain.util

sealed class QueryResult<T> {
    class Success<T>(val value: T) : QueryResult<T>()
    class Fail<T>(val msg: String) : QueryResult<T>()
}
