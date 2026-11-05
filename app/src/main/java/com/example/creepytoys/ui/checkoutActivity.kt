package com.example.creepytoys.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.creepytoys.R
import com.example.creepytoys.data.CartPreferences
import com.google.android.material.textfield.TextInputEditText

class CheckoutActivity : AppCompatActivity() {

    private lateinit var cartPreferences: CartPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_checkout)

        cartPreferences = CartPreferences(this)

        val etFullName = findViewById<TextInputEditText>(R.id.et_full_name)
        val etAddress = findViewById<TextInputEditText>(R.id.et_address)
        val rgPayment = findViewById<RadioGroup>(R.id.rg_payment_methods)
        val rbCard = findViewById<RadioButton>(R.id.rb_card)
        val containerCard = findViewById<LinearLayout>(R.id.container_card_fields)
        val etCardNumber = findViewById<TextInputEditText>(R.id.et_card_number)
        val etCardExp = findViewById<TextInputEditText>(R.id.et_card_exp)
        val etCardCvv = findViewById<TextInputEditText>(R.id.et_card_cvv)
        val tvTotal = findViewById<TextView>(R.id.tv_checkout_total)
        val btnConfirm = findViewById<Button>(R.id.btn_confirm_order)

        // Recuperar el total enviado desde CatalogActivity
        val totalAmount = intent.getDoubleExtra("EXTRA_TOTAL_AMOUNT", 0.0)
        tvTotal.text = "Total a Pagar: $$totalAmount"

        // Mostrar / Ocultar datos de tarjeta según método seleccionado
        rgPayment.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_card) {
                containerCard.visibility = View.VISIBLE
            } else {
                containerCard.visibility = View.GONE
            }
        }

        btnConfirm.setOnClickListener {
            val name = etFullName.text.toString().trim()
            val address = etAddress.text.toString().trim()

            // Validación QA 1: Campos de dirección obligatorios
            if (name.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Por favor ingresa nombre y dirección de envío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación QA 2: Campos de tarjeta si elige pago con tarjeta
            if (rbCard.isChecked) {
                val cardNum = etCardNumber.text.toString().trim()
                val cardExp = etCardExp.text.toString().trim()
                val cardCvv = etCardCvv.text.toString().trim()

                if (cardNum.length < 16 || cardExp.isEmpty() || cardCvv.length < 3) {
                    Toast.makeText(this, "Por favor completa datos de tarjeta válidos", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Simulación de Pedido Exitoso
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("¡Pedido Creepy Confirmado! 🔪")
            .setMessage("Tu orden ha sido procesada con éxito. Los espíritus llevarán tus juguetes pronto.")
            .setCancelable(false)
            .setPositiveButton("Aceptar") { _, _ ->
                // Vaciar carrito
                cartPreferences.clearAll(listOf()) // Limpia todos los registros
                setResult(RESULT_OK)
                finish()
            }
            .show()
    }
}