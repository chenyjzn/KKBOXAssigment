package com.yuchen.kkbox.data

sealed class RepoResult<out R> {

    data class Success<out T>(val data: T) : RepoResult<T>()
    data class Err(val error: String) : RepoResult<Nothing>()
    data class Except(val exception: Exception) : RepoResult<Nothing>()
    object Loading : RepoResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[result=$data]"
            is Err -> "Fail[error=$error]"
            is Except -> "Error[exception=${exception.message}]"
            Loading -> "Loading"
        }
    }
}

val RepoResult<*>.succeeded
    get() = this is RepoResult.Success && data != null