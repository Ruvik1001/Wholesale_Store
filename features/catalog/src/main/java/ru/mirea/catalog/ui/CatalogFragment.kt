package ru.mirea.catalog.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.mirea.catalog.adapter.CategoryAdapter
import ru.mirea.catalog.adapter.ProductAdapter
import ru.mirea.catalog.R
import ru.mirea.core.checkInternet
import ru.mirea.core.hideInputBoard
import ru.mirea.domain.market.data.CartItem
import ru.mirea.domain.market.data.Product

class CatalogFragment : Fragment() {
    private val viewModel by inject<CatalogViewModel>()

    private lateinit var view: View

    private lateinit var btnProfile: ImageView
    private lateinit var btnBasket: ImageView

    private lateinit var ibBack: ImageButton
    private lateinit var etItemName: EditText
    private lateinit var ibClear: ImageButton
    private lateinit var ibSearch: ImageButton
    private lateinit var rvItems: RecyclerView

    private lateinit var itemCard: RelativeLayout
    private lateinit var itemPhotoCard: ImageView
    private lateinit var ibBackCard: ImageButton
    private lateinit var tvCardTitle: TextView
    private lateinit var tvCardDescription: TextView
    private lateinit var tvCardCost: TextView
    private lateinit var btnAddToBasket: Button

    private var _category: String? = null
    private var _subcategory: String? = null
    private var _product: Product? = null

    private enum class INNER_LEVEL {
        CATEGORIES,
        SUBCATEGORIES,
        PRODUCTS,
        PRODUCT
    }

    private var level = INNER_LEVEL.CATEGORIES

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_catalog, container, false)
        checkInternet(requireContext())

        ibBack = view.findViewById(R.id.ibBack)
        etItemName = view.findViewById(R.id.etItemName)
        ibClear = view.findViewById(R.id.ibClear)
        ibSearch = view.findViewById(R.id.ibSearch)
        rvItems = view.findViewById(R.id.rvItems)

        itemCard = view.findViewById(R.id.itemCard)
        itemCard.visibility = RelativeLayout.GONE

        ibBackCard = view.findViewById(R.id.ibBackCard)
        itemPhotoCard = view.findViewById(R.id.ItemPhoto)
        tvCardTitle = view.findViewById(R.id.itemTitle)
        tvCardDescription = view.findViewById(R.id.itemDescription)
        tvCardCost = view.findViewById(R.id.itemCost)
        btnAddToBasket = view.findViewById(R.id.btnAddItemInBasket)

        btnProfile = view.findViewById(ru.mirea.core.R.id.ivProfile)
        btnBasket = view.findViewById(ru.mirea.core.R.id.ivBasket)

        btnProfile.setOnClickListener { viewModel.lunchProfile() }
        btnBasket.setOnClickListener { viewModel.lunchBasket() }

        viewModel.loadCategories()

        rvItems.layoutManager = LinearLayoutManager(requireContext())

        ibBack.setOnClickListener { goBack() }


        etItemName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) ibClear.visibility = View.VISIBLE
                else ibClear.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }

        })

        ibClear.setOnClickListener {
            etItemName.clearFocus()
            etItemName.text.clear()
            hideInputBoard(requireContext(), etItemName.windowToken)
            goBack()
        }

        ibSearch.setOnClickListener {
            etItemName.clearFocus()
            hideInputBoard(requireContext(), etItemName.windowToken)
            val query = etItemName.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchProductsByName(query)
                level = INNER_LEVEL.PRODUCTS
                ibBack.visibility = ImageButton.VISIBLE
            }
            else goBack()
        }

        viewModel.products.observe(viewLifecycleOwner) { products ->
            val productAdapter = ProductAdapter(products) { product ->
                _product = product
                level = INNER_LEVEL.PRODUCT
                configureProductCard(_product!!)
            }
            ibBack.visibility = ImageButton.VISIBLE
            level = INNER_LEVEL.PRODUCTS
            rvItems.adapter = productAdapter
        }

        viewModel.subcategories.observe(viewLifecycleOwner) { subcategories ->
            val subcategoryAdapter = CategoryAdapter(subcategories) { subcategory ->
                _subcategory = subcategory
                viewModel.loadProducts(category = _category!!, subcategory = subcategory)
            }
            ibBack.visibility = ImageButton.VISIBLE
            level = INNER_LEVEL.SUBCATEGORIES
            rvItems.adapter = subcategoryAdapter
        }

        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            val categoryAdapter = CategoryAdapter(categories) { category ->
                _category = category
                viewModel.loadSubcategories(category)
            }
            ibBack.visibility = ImageButton.GONE
            level = INNER_LEVEL.CATEGORIES
            rvItems.adapter = categoryAdapter
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { searchResults ->
            val productAdapter = ProductAdapter(searchResults) { product ->
                _product = product
                configureProductCard(_product!!)
            }
            ibBack.visibility = ImageButton.VISIBLE
            rvItems.adapter = productAdapter
        }

        return view
    }

    private fun configureProductCard(product: Product) {
        itemCard.visibility = RelativeLayout.VISIBLE
        ibBackCard.setOnClickListener {
            itemCard.visibility = RelativeLayout.GONE
            goBack()
        }
        Glide.with(this)
            .load(_product!!.photoUrl)
            .apply(RequestOptions().placeholder(ru.mirea.core.R.drawable.default_photo))
            .error(ru.mirea.core.R.drawable.default_photo)
            .into(itemPhotoCard)
        tvCardTitle.text = product.name
        tvCardDescription.text = product.description
        tvCardCost.text = "${product.cost.toString()} ₽"
        btnAddToBasket.setOnClickListener {
            viewModel.addCartItem(CartItem(
                productId = product.code,
                productName = product.name,
                quantity = 1
            ))
            ibBackCard.performClick()
            Toast.makeText(view.context, "Товар добаввлен в корзину", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goBack() {
        if (level == INNER_LEVEL.CATEGORIES) return
        else if (level == INNER_LEVEL.SUBCATEGORIES) {
            _subcategory = null
            viewModel.loadCategories()
        }
        else if (level == INNER_LEVEL.PRODUCTS) {
            _subcategory = null
            viewModel.loadSubcategories(_category!!)
        }
        else if (level == INNER_LEVEL.PRODUCT) {
            _product = null
            viewModel.loadProducts(_category!!, _subcategory!!)
        }

    }

    companion object {
        private const val TAG = "CATALOG_FRAGMENT"
    }
}