package com.example.mobv.data

import android.content.Context
import com.example.mobv.model.Pub
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class PubDatasource(val context: Context) {
    private val jsonPath: String = "pubs.json"

    fun loadPubs(): ArrayList<Pub> {
        val json: String = context.assets.open(jsonPath).bufferedReader().use { it.readText() }
        val typeToken = object : TypeToken<List<Pub>>() {}.type
        return Gson().fromJson(json, typeToken)
    }
}