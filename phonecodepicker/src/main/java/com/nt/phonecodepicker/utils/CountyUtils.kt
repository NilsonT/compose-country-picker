package com.nt.phonecodepicker.utils


import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.util.Locale

fun defaultCountry(): Country {
    return Country(
        nameEs = "Venezuela",
        nameEn = "Venezuela", dialCode = "+58", code = "VE"
    )
}

fun countryList(context: Context): MutableList<Country> {
    val jsonFileString = getJsonDataFromAsset(context, "Countries.json")
    val type = object : TypeToken<List<Country>>() {}.type
    val list: MutableList<Country> = Gson().fromJson(jsonFileString, type)
    list.sortBy {
        when (Locale.getDefault().language.lowercase()) {
            "en" -> it.nameEn
            "es" -> it.nameEs
            else -> it.nameEn
        }
    }
    return list
}

fun getJsonDataFromAsset(context: Context, fileName: String): String? {
    val jsonString: String
    try {
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return null
    }
    return jsonString
}

fun List<Country>.searchCountry(countryName: String): List<Country> {
    val countryList = ArrayList<Country>()
    forEach {
        if (it.nameEn.lowercase().contains(countryName.lowercase()) ||
            it.nameEs.lowercase().contains(countryName.lowercase()) ||
            it.dialCode.contains(countryName.lowercase())
        ) {
            countryList.add(it)
        }
    }
    countryList.sortBy {
        when (Locale.getDefault().language.lowercase()) {
            "en" -> it.nameEn
            "es" -> it.nameEs
            else -> it.nameEn
        }
    }
    return countryList
}

data class Country(
    @SerializedName("name_en")
    @Expose
    val nameEn: String = "",
    @SerializedName("name_es")
    @Expose
    val nameEs: String = "",
    @SerializedName("dial_code")
    @Expose
    val dialCode: String = "",
    @SerializedName("code")
    @Expose
    val code: String = "",
)