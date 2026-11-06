package com.example.creepytoys.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.creepytoys.R
import com.example.creepytoys.data.CartPreferences
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {

    private lateinit var cartPreferences: CartPreferences

    // Lista de IDs de los productos para limpiar el SharedPreferences
    private val allToyIds = listOf(
        "toy_001", "toy_002", "toy_003", "toy_004", "toy_005", "toy_006",
        "toy_007", "toy_008", "toy_009", "toy_010", "toy_011", "toy_012"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        cartPreferences = CartPreferences(this)

        // Configuración Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_checkout)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Referencias a los componentes según tu XML
        val etFullName = findViewById<TextInputEditText>(R.id.et_full_name)
        val etAddress = findViewById<TextInputEditText>(R.id.et_address)
        val rgPaymentMethods = findViewById<RadioGroup>(R.id.rg_payment_methods)
        val rbCard = findViewById<RadioButton>(R.id.rb_card)
        val containerCardFields = findViewById<LinearLayout>(R.id.container_card_fields)
        val etCardNumber = findViewById<TextInputEditText>(R.id.et_card_number)
        val etCardExp = findViewById<TextInputEditText>(R.id.et_card_exp)
        val etCardCvv = findViewById<TextInputEditText>(R.id.et_card_cvv)
        val tvTotal = findViewById<TextView>(R.id.tv_checkout_total)
        val btnConfirmOrder = findViewById<Button>(R.id.btn_confirm_order)

        // 1. Recibir y mostrar el monto total
        val totalAmount = intent.getDoubleExtra("EXTRA_TOTAL_AMOUNT", 0.0)
        tvTotal.text = String.format(Locale.getDefault(), "Total a Pagar: $%.2f", totalAmount)

        // 2. Control visual: Mostrar/Ocultar campos de Tarjeta según la selección
        rgPaymentMethods.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_card) {
                containerCardFields.visibility = View.VISIBLE
            } else {
                containerCardFields.visibility = View.GONE
            }
        }

        // 3. Confirmación del pedido con validaciones
        btnConfirmOrder.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val address = etAddress.text.toString().trim()

            if (fullName.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Por favor completa la dirección de envío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Si eligió tarjeta, se validan los campos de la tarjeta
            if (rbCard.isChecked) {
                val cardNumber = etCardNumber.text.toString().trim()
                val cardExp = etCardExp.text.toString().trim()
                val cardCvv = etCardCvv.text.toString().trim()

                if (cardNumber.length < 16 || cardExp.isEmpty() || cardCvv.length < 3) {
                    Toast.makeText(this, "Por favor completa correctamente los datos de la tarjeta", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Limpiar datos del carrito
            cartPreferences.clearAll(allToyIds)

            Toast.makeText(this, "¡Gracias por tu compra, $fullName! Tu pedido ha sido confirmado.", Toast.LENGTH_LONG).show()

            // Regresar al Catálogo limpiando la pila de actividades
            val intent = Intent(this, CatalogActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}