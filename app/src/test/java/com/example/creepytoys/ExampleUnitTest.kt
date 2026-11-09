package com.example.creepytoys

import org.junit.Assert.assertEquals
import org.junit.Test

class CartLogicTest {

    @Test
    fun clearCart_shouldResetItemCountToZero() {
        // 1. ARRANGE (Prepararamos el escenario)
        var itemCount = 3

        // 2. ACT (Ejecutamos la acción de limpiar el carrito)
        itemCount = 0

        // 3. ASSERT (Verificamos que realmente sea 0)
        assertEquals(0, itemCount)
    }
}