package com.example.creepytoys.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.creepytoys.R
import com.example.creepytoys.data.CartPreferences

class CheckoutActivity : AppCompatActivity() {

    private lateinit var etFullName: EditText
    private lateinit var etAddress: EditText
    private lateinit var rgPaymentMethods: RadioGroup
    private lateinit var rbCard: RadioButton
    private lateinit var rbCash: RadioButton
    private lateinit var containerCardFields: LinearLayout
    private lateinit var etCardNumber: EditText
    private lateinit var etCardExp: EditText
    private lateinit var etCardCvv: EditText
    private lateinit var tvCheckoutTotal: TextView
    private lateinit var btnConfirmOrder: Button

    private lateinit var cartPreferences: CartPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        cartPreferences = CartPreferences(this)

        // 1. Vincular vistas con sus IDs
        etFullName = findViewById(R.id.et_full_name)
        etAddress = findViewById(R.id.et_address)
        rgPaymentMethods = findViewById(R.id.rg_payment_methods)
        rbCard = findViewById(R.id.rb_card)
        rbCash = findViewById(R.id.rb_cash)
        containerCardFields = findViewById(R.id.container_card_fields)
        etCardNumber = findViewById(R.id.et_card_number)
        etCardExp = findViewById(R.id.et_card_exp)
        etCardCvv = findViewById(R.id.et_card_cvv)
        tvCheckoutTotal = findViewById(R.id.tv_checkout_total)
        btnConfirmOrder = findViewById(R.id.btn_confirm_order)

        // 2. Cargar y mostrar el Total real a pagar
        displayTotalAmount()

        // 3. Formateador automático de fecha
        setupExpiryDateAutoFormat()

        // 4. Cambiar visibilidad de campos según el método de pago
        rgPaymentMethods.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_card) {
                containerCardFields.visibility = View.VISIBLE
            } else {
                containerCardFields.visibility = View.GONE
            }
        }

        btnConfirmOrder.setOnClickListener {
            if (validateForm()) {
                val userName = etFullName.text.toString().trim()
                processOrder(userName)
            }
        }
    }

    private fun displayTotalAmount() {
        // Obtenemos el total enviado desde la pantalla anterior por Intent Extra
        val totalAmount = intent.getDoubleExtra("EXTRA_TOTAL_AMOUNT", 0.0)
        tvCheckoutTotal.text = String.format("Total a Pagar: $%.2f", totalAmount)
    }

    private fun setupExpiryDateAutoFormat() {
        etCardExp.addTextChangedListener(object : TextWatcher {
            private var isFormatting = false

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isFormatting || s == null) return
                isFormatting = true

                val clean = s.toString().replace("/", "")

                if (clean.length >= 2) {
                    val formatted = "${clean.substring(0, 2)}/${clean.substring(2)}"
                    s.replace(0, s.length, formatted)
                } else {
                    s.replace(0, s.length, clean)
                }

                isFormatting = false
            }
        })
    }

    private fun validateForm(): Boolean {
        val name = etFullName.text.toString().trim()
        val address = etAddress.text.toString().trim()

        if (name.length < 10) {
            etFullName.error = "Ingresa un nombre y apellido válidos (mínimo 10 caracteres)"
            etFullName.requestFocus()
            return false
        }

        if (address.length < 10) {
            etAddress.error = "La dirección es muy corta. Ingresa calle, número y colonia/ciudad"
            etAddress.requestFocus()
            return false
        }

        if (rbCard.isChecked) {
            val cardNumber = etCardNumber.text.toString().trim()
            val expiryDate = etCardExp.text.toString().trim()
            val cvc = etCardCvv.text.toString().trim()

            if (cardNumber.length < 16) {
                etCardNumber.error = "El número de tarjeta debe tener 16 dígitos"
                etCardNumber.requestFocus()
                return false
            }

            val expiryRegex = Regex("""^(0[1-9]|1[0-2])/[0-9]{2}$""")
            if (expiryDate.isEmpty() || !expiryDate.matches(expiryRegex)) {
                etCardExp.error = "Ingresa una fecha válida en formato MM/AA"
                etCardExp.requestFocus()
                return false
            }

            if (cvc.length < 3) {
                etCardCvv.error = "El CVV debe tener al menos 3 dígitos"
                etCardCvv.requestFocus()
                return false
            }
        }

        return true
    }

    private fun processOrder(userName: String) {
        // Limpieza garantizada de SharedPreferences
        val sharedPreferences = getSharedPreferences("creepy_toys_cart_prefs", MODE_PRIVATE)
        sharedPreferences.edit().clear().commit() // Usamos commit() para asegurar persistencia síncrona inmediata

        AlertDialog.Builder(this)
            .setTitle("¡Pedido Confirmado!")
            .setMessage("¡Gracias por tu compra, $userName! Tu pedido de Creepy Toys ha sido procesado con éxito.")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
                setResult(RESULT_OK) // Notifica a la pantalla anterior que la compra fue exitosa
                finish()
            }
            .setCancelable(false)
            .show()
    }
}