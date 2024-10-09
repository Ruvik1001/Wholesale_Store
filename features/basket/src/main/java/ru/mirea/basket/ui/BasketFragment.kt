package ru.mirea.basket.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import ru.mirea.basket.R
import ru.mirea.basket.adapter.BasketAdapter
import ru.mirea.core.checkInternet
import ru.mirea.core.toastValidateAnyFiled
import ru.mirea.core.validateNamePattern

class BasketFragment : Fragment() {
    private val viewModel by inject<BasketViewModel>()

    private lateinit var view: View
    private lateinit var btnProfile: ImageView
    private lateinit var btnCatalog: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var rvItems: RecyclerView
    private lateinit var btnTakeOrder: Button

    private lateinit var ibCardOrderBack: ImageButton
    private lateinit var llOrderCart: LinearLayout
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var btnConfirmOrder: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_basket, container, false)
        checkInternet(requireContext())

        btnProfile = view.findViewById(ru.mirea.core.R.id.ivProfile)
        btnCatalog = view.findViewById(ru.mirea.core.R.id.ivCatalog)

        tvTitle = view.findViewById(R.id.tvTitle)
        tvTitle.text = getString(R.string.page_title_basket)

        rvItems = view.findViewById(R.id.rvItems)
        rvItems.layoutManager = LinearLayoutManager(requireContext())

        btnTakeOrder = view.findViewById(R.id.btnTakeOrder)

        ibCardOrderBack = view.findViewById(R.id.ibCardOrderBack)
        llOrderCart = view.findViewById(R.id.orderCart)
        etName = view.findViewById(R.id.etName)
        etPhone = view.findViewById(R.id.etPhone)
        btnConfirmOrder = view.findViewById(R.id.btnConfirmOrder)

        btnProfile.setOnClickListener { viewModel.lunchProfile() }
        btnCatalog.setOnClickListener { viewModel.lunchCatalog() }

        ibCardOrderBack.setOnClickListener {
            etName.text.clear()
            etPhone.text.clear()
            llOrderCart.visibility = LinearLayout.GONE
        }

        btnTakeOrder.setOnClickListener {
            llOrderCart.visibility = LinearLayout.VISIBLE
        }

        btnConfirmOrder.setOnClickListener {
            if (!toastValidateAnyFiled(
                context = view.context,
                value = etName.text.toString(),
                { it ->
                    validateNamePattern(it)
                },
                    getString(R.string.wrong_name_format)
            )) return@setOnClickListener

            if (!toastValidateAnyFiled(
                    context = view.context,
                    value = etPhone.text.toString(),
                    { it ->
                        var counter = 0
                        for (char in it) if (char.isDigit()) counter++
                        counter == 11
                    },
                    getString(R.string.wrong_phone_format)
                )) return@setOnClickListener

            viewModel.createOrder(
                ownerName = etName.text.toString(),
                ownerPhone = etPhone.text.toString(),
                ) {
                Toast.makeText(
                    view.context,
                    if (it) getString(R.string.order_success) else getString(R.string.order_failure),
                    Toast.LENGTH_SHORT
                ).show()

                if (!it) return@createOrder

                llOrderCart.visibility = LinearLayout.GONE
                AlertDialog.Builder(view.context)
                    .setTitle(getString(R.string.order))
                    .setMessage(getString(R.string.success_order_formed))
                    .setPositiveButton(ru.mirea.core.R.string.OK) { _, _ -> }
                    .create().show()
            }
        }

        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            if (viewModel.cartItems.value!!.size > 0) btnTakeOrder.visibility = Button.VISIBLE
            else btnTakeOrder.visibility = Button.GONE

            rvItems.adapter = BasketAdapter(
                items,
                onIncrease = { cartItem -> viewModel.addCartItem(cartItem) },
                onDecrease = { cartItem -> viewModel.removeCartItem(cartItem) }
            )
        }

        viewModel.loadCartItems()

        return view
    }
}


