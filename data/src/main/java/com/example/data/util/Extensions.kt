package com.example.data.util

// language features should be in a separate module e.g. common,
// so that both app and data can depend on it
val String.Companion.EMPTY: String get() = ""