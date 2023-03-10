package com.bohdanov.bootounter.data

import com.bohdanov.bootounter.models.BootData

interface BootDataRepository {
    fun saveBootData(timestamp: Long)
    fun getSavedBoots(): List<BootData>
    fun clearData()
}