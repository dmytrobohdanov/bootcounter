package com.bohdanov.bootounter.utils

import com.bohdanov.bootounter.models.BootData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun List<BootData>.serialize(): String {
    return Gson().toJson(this)
}

fun String.deserializeToBootDataList(): List<BootData> {
    return Gson().fromJson(this, object : TypeToken<List<BootData>>() {}.type)
}

