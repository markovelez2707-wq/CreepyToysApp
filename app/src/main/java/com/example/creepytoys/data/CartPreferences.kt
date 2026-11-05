package com.example.creepytoys.data

import android.content.Context
import android.content.SharedPreferences

class CartPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("creepy_toys_cart_prefs", Context.MODE_PRIVATE)

    fun getQuantity(toyId: String): Int {
        return prefs.getInt("QTY_$toyId", 0)
    }

    fun setQuantity(toyId: String, quantity: Int) {
        if (quantity <= 0) {
            prefs.edit().remove("QTY_$toyId").apply()
        } else {
            prefs.edit().putInt("QTY_$toyId", quantity).apply()
        }
    }

    fun getTotalItemCount(allToyIds: List<String>): Int {
        var total = 0
        for (id in allToyIds) {
            total += getQuantity(id)
        }
        return total
    }

    fun clearAll(allToyIds: List<String>) {
        val editor = prefs.edit()
        for (id in allToyIds) {
            editor.remove("QTY_$id")
        }
        editor.apply()
    }
}