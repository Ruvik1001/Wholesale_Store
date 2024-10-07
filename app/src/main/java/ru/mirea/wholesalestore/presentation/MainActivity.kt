package ru.mirea.wholesalestore.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.mirea.core.initTheme
import ru.mirea.forgot_password.ForgotPasswordRouter
import ru.mirea.profile.ProfileRouter
import ru.mirea.wholesalestore.R
import ru.mirea.wholesalestore.glue.AdapterSignInRouter
import ru.mirea.sign_in.SignInRouter
import ru.mirea.sign_up.SignUpRouter
import ru.mirea.wholesalestore.glue.AdapterForgotPasswordRouter
import ru.mirea.wholesalestore.glue.AdapterProfileRouter
import ru.mirea.wholesalestore.glue.AdapterSignUpRouter

class MainActivity : AppCompatActivity() {
    private val adapterSignInRouter: SignInRouter by inject()
    private val adapterSignUpRouter: SignUpRouter by inject()
    private val adapterForgotPasswordRouter: ForgotPasswordRouter by inject()
    private val adapterProfileRouter: ProfileRouter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initTheme(applicationContext)
        CoroutineScope(Dispatchers.Main).launch {
            init()
        }
    }

    private fun init() {
        val navController = findNavController(R.id.fragmentContainerView)
        (adapterSignInRouter as AdapterSignInRouter).switchNavController(navController)
        (adapterSignUpRouter as AdapterSignUpRouter).switchNavController(navController)
        (adapterForgotPasswordRouter as AdapterForgotPasswordRouter).switchNavController(navController)
        (adapterProfileRouter as AdapterProfileRouter).switchNavController(navController)
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.fragmentContainerView).popBackStack()) {
            super.onBackPressed()
        }
    }

}