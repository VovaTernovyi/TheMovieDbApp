package com.themoviedbapp.extension

import com.themoviedbapp.BuildConfig
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException

inline fun <T> Result<T>.onError(action: (exception: Throwable) -> Unit) {
    this.exceptionOrNull()?.let {
        if (it !is CancellationException && (it as? HttpException)?.code() != 401)
            action(it)
    }
}

inline fun onNotReleaseBuild(block: () -> Unit) {
    if (BuildConfig.DEBUG) {
        block()
    }
}