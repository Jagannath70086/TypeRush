package com.typer.typerush.core.api

import com.typer.typerush.BuildConfig


fun getUrl(url: String): String {
    return when {
        url.startsWith("/") -> BuildConfig.BASE_API_URL + url.drop(1)
        url.contains(BuildConfig.BASE_API_URL) -> url
        else -> BuildConfig.BASE_API_URL + url
    }
}