package com.example.creepytoys.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.creepytoys.R
import com.example.creepytoys.data.CartPreferences
import com.example.creepytoys.domain.model.ToyItem
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.ChipGroup

@OptIn(ExperimentalBadgeUtils::class)
class CatalogActivity : AppCompatActivity() {

    private lateinit var cartPreferences: CartPreferences
    private lateinit var adapter: ToyAdapter
    private var fullToyList: List<ToyItem> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        setContentView(R.layout.activity_catalog)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.inflateMenu(R.menu.menu_catalog)

        toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_cart) {
                showCartBottomSheet()
                true
            } else {
                false
            }
        }

        cartPreferences = CartPreferences(this)
        setupData()
        setupRecyclerView()
        setupFilterChips()
        updateTotalCartCountUI()
    }

    override fun onResume() {
        super.onResume()
        updateTotalCartCountUI()
    }

    private fun setupData() {
        fullToyList = listOf(
            ToyItem(
                id = "toy_001",
                name = "Muñeco Chucky y Tifanny Poseídos",
                franchise = "Chucky",
                price = 2400.00,
                stock = 5,
                minAge = 18,
                imageResId = R.drawable.img_chucky_tifanny,
                contentDescription = "Muñecos Tifanny y Chucky originales de la película"
            ),
            ToyItem(
                id = "toy_002",
                name = "Chico bueno Chucky",
                franchise = "Chucky",
                price = 1500.00,
                stock = 8,
                minAge = 18,
                imageResId = R.drawable.img_chico_bueno,
                contentDescription = "Muñeco Chucky con ropa de tela y overall clásico de la película"
            ),
            ToyItem(
                id = "toy_003",
                name = "Chucky copiloto",
                franchise = "Chucky",
                price = 1500.00,
                stock = 8,
                minAge = 18,
                imageResId = R.drawable.img_chucky_copiloto,
                contentDescription = "Muñeco Chucky copiloto original 1991"
            ),
            ToyItem(
                id = "toy_004",
                name = "Freddy, Bunny y Foxy",
                franchise = "FNAF",
                price = 2300.00,
                stock = 10,
                minAge = 18,
                imageResId = R.drawable.img_freddy_bunny_foxy,
                contentDescription = "Muñecos de peluche de la franquicia FNAF"
            ),
            ToyItem(
                id = "toy_005",
                name = "Freddy",
                franchise = "FNAF",
                price = 900.00,
                stock = 15,
                minAge = 18,
                imageResId = R.drawable.img_freddy,
                contentDescription = "Muñeco Freddy de peluche de la franquicia FNAF"
            ),
            ToyItem(
                id = "toy_006",
                name = "Set Springtrap FNAF",
                franchise = "FNAF",
                price = 2600.00,
                stock = 9,
                minAge = 18,
                imageResId = R.drawable.img_spirngtrap,
                contentDescription = "Set terrorífico Springtrap"
            ),
            ToyItem(
                id = "toy_007",
                name = "Set Smiley critters",
                franchise = "Poppy Playtime",
                price = 3200.00,
                stock = 5,
                minAge = 18,
                imageResId = R.drawable.img_smiley_cirtters,
                contentDescription = "Set de 8 piezas juguetes del videojuego Poppy Playtime"
            ),
            ToyItem(
                id = "toy_008",
                name = "Huggy Wuggy",
                franchise = "Poppy Playtime",
                price = 900.00,
                stock = 12,
                minAge = 18,
                imageResId = R.drawable.img_huggy_wuggy,
                contentDescription = "Peluche original Huggy del videojuego Poppy Playtime"
            ),
            ToyItem(
                id = "toy_009",
                name = "Catnap",
                franchise = "Poppy Playtime",
                price = 900.00,
                stock = 5,
                minAge = 18,
                imageResId = R.drawable.img_catnap,
                contentDescription = "Peluche morado catnap del videojuego Poppy Playtime"
            ),
            ToyItem(
                id = "toy_010",
                name = "Cajas sorpresa",
                franchise = "Stranger things",
                price = 3000.00,
                stock = 13,
                minAge = 18,
                imageResId = R.drawable.img_surprise_box,
                contentDescription = "Cajas sorpresa con tv y un muñeco al azar de la serie Stranger things"
            ),
            ToyItem(
                id = "toy_011",
                name = "Holder Demogorgon",
                franchise = "Stranger things",
                price = 800.00,
                stock = 20,
                minAge = 18,
                imageResId = R.drawable.img_holder_demo,
                contentDescription = "Holder para control de consola o celular Demogorgon"
            ),
            ToyItem(
                id = "toy_012",
                name = "Lámpara Demogorgon",
                franchise = "Stranger things",
                price = 1200.00,
                stock = 11,
                minAge = 18,
                imageResId = R.drawable.img_lampara_demo,
                contentDescription = "Lámpara de escritorio Demogorgon"
            )
        )
    }

    private fun setupRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rv_toy_catalog)

        adapter = ToyAdapter(
            toyList = fullToyList,
            onAddToCartClick = { selectedToy ->
                val currentQty = cartPreferences.getQuantity(selectedToy.id)
                cartPreferences.setQuantity(selectedToy.id, currentQty + 1)
                updateTotalCartCountUI()
                Toast.makeText(this, "${selectedToy.name} añadido al carrito", Toast.LENGTH_SHORT).show()
            }
        )

        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2

        recyclerView.layoutManager = GridLayoutManager(this, spanCount)
        recyclerView.adapter = adapter
    }

    private fun setupFilterChips() {
        val chipGroup: ChipGroup = findViewById(R.id.chip_group_filter)

        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isEmpty()) {
                adapter.updateList(fullToyList)
                return@setOnCheckedStateChangeListener
            }

            val selectedChipId = checkedIds.first()
            val filteredList = when (selectedChipId) {
                R.id.chip_chucky -> fullToyList.filter { it.franchise.equals("Chucky", ignoreCase = true) }
                R.id.chip_fnaf -> fullToyList.filter { it.franchise.equals("FNAF", ignoreCase = true) }
                R.id.chip_poppy -> fullToyList.filter { it.franchise.equals("Poppy Playtime", ignoreCase = true) }
                R.id.chip_stranger -> fullToyList.filter { it.franchise.equals("Stranger things", ignoreCase = true) }
                else -> fullToyList
            }

            adapter.updateList(filteredList)
        }
    }

    private fun updateTotalCartCountUI() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        if (toolbar.menu.findItem(R.id.action_cart) == null) return

        val totalCount = fullToyList.sumOf { toy ->
            cartPreferences.getQuantity(toy.id)
        }

        if (totalCount > 0) {
            val badgeDrawable = BadgeDrawable.create(this).apply {
                number = totalCount
                backgroundColor = android.graphics.Color.RED
                badgeTextColor = android.graphics.Color.WHITE
                isVisible = true
            }
            BadgeUtils.attachBadgeDrawable(badgeDrawable, toolbar, R.id.action_cart)
        } else {
            BadgeUtils.detachBadgeDrawable(null, toolbar, R.id.action_cart)
        }
    }

    private fun showCartBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_cart, null)

        val rvCart = view.findViewById<RecyclerView>(R.id.rv_cart_items)
        val tvTotal = view.findViewById<TextView>(R.id.tv_cart_total)
        val btnClear = view.findViewById<Button>(R.id.btn_clear_cart)
        val btnCheckout = view.findViewById<Button>(R.id.btn_checkout)

        var cartAdapter: CartAdapter? = null

        fun refreshCartList() {
            val itemsInCart = fullToyList.mapNotNull { toy ->
                val qty = cartPreferences.getQuantity(toy.id)
                if (qty > 0) Pair(toy, qty) else null
            }

            val totalCost = itemsInCart.sumOf { it.first.price * it.second }
            tvTotal.text = "Total: $$totalCost"

            updateTotalCartCountUI()

            if (cartAdapter != null) {
                cartAdapter?.updateData(itemsInCart)
            } else {
                cartAdapter = CartAdapter(
                    cartItems = itemsInCart,
                    onIncreaseClick = { selectedToy: ToyItem ->
                        val currentQty = cartPreferences.getQuantity(selectedToy.id)
                        cartPreferences.setQuantity(selectedToy.id, currentQty + 1)
                        refreshCartList()
                    },
                    onDecreaseClick = { selectedToy: ToyItem ->
                        val currentQty = cartPreferences.getQuantity(selectedToy.id)
                        cartPreferences.setQuantity(selectedToy.id, currentQty - 1)
                        refreshCartList()
                    }
                )
                rvCart.layoutManager = LinearLayoutManager(this)
                rvCart.adapter = cartAdapter
            }
        }

        btnClear.setOnClickListener {
            cartPreferences.clearAll(fullToyList.map { it.id })
            refreshCartList()
            dialog.dismiss()
        }

        btnCheckout.setOnClickListener {
            val itemsInCart = fullToyList.mapNotNull { toy ->
                val qty = cartPreferences.getQuantity(toy.id)
                if (qty > 0) Pair(toy, qty) else null
            }

            if (itemsInCart.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val totalCost = itemsInCart.sumOf { it.first.price * it.second }

            val intent = Intent(this, CheckoutActivity::class.java).apply {
                putExtra("EXTRA_TOTAL_AMOUNT", totalCost)
            }
            dialog.dismiss()
            startActivity(intent)
        }

        refreshCartList()
        dialog.setContentView(view)
        dialog.show()
    }
}