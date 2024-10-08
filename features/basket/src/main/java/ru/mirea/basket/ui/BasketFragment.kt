package ru.mirea.basket.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.android.ext.android.inject
import ru.mirea.basket.R
import ru.mirea.basket.adapter.BasketAdapter
import ru.mirea.core.checkInternet

class BasketFragment : Fragment() {
    private val viewModel by inject<BasketViewModel>()

    private lateinit var view: View
    private lateinit var btnProfile: ImageView
    private lateinit var btnCatalog: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var rvItems: RecyclerView

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


        btnProfile.setOnClickListener { viewModel.lunchProfile() }
        btnCatalog.setOnClickListener { viewModel.lunchCatalog() }

        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            rvItems.adapter = BasketAdapter(
                items,
                onIncrease = { cartItem -> viewModel.addCartItem(cartItem) },
                onDecrease = { cartItem -> viewModel.removeCartItem(cartItem) }
            )
        }

        viewModel.loadCartItems()

        return view
    }

    private fun observeCartItems() {

    }
}
