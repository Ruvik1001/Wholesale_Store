package ru.mirea.sign_in.ui

import android.app.AlertDialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject
import ru.mirea.core.alertCheckConnection
import ru.mirea.core.isEnableViewSwitch
import ru.mirea.core.toastValidateAnyFiled
import ru.mirea.core.validateMailPattern
import ru.mirea.core.validatePasswordPattern
import ru.mirea.sign_in.R

class SignInFragment : Fragment() {

    private val viewModel by inject<SignInViewModel>()

    private lateinit var view: View

    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button
    private lateinit var btnForgotPassword: TextView

    private lateinit var progressBar: ProgressBar

    private lateinit var btnList: List<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        etLogin = view.findViewById(R.id.editTextLogin)
        etPassword = view.findViewById(R.id.editTextPassword)
        btnSignIn = view.findViewById(R.id.btnSignIn)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        btnForgotPassword = view.findViewById(R.id.tvForgotPassword)

        progressBar = view.findViewById(R.id.progress_circular)

        btnList = listOf(btnSignIn, btnSignUp, btnForgotPassword)

        btnSignUp.setOnClickListener { viewModel.lunchSignUp() }
        btnForgotPassword.setOnClickListener { viewModel.lunchForgotPassword() }

        btnSignIn.setOnClickListener {
            val login = etLogin.text.toString()
            val password = etPassword.text.toString()

            if (!validate(login, password)) return@setOnClickListener
            if (!alertCheckConnection(requireContext())) return@setOnClickListener

            progressBar.visibility = ProgressBar.VISIBLE
            isEnableViewSwitch(btnList)

            viewModel.signIn(login = login, password = password) { success ->
                progressBar.visibility = ProgressBar.GONE
                isEnableViewSwitch(btnList)

                if (success != null) viewModel.lunchHome()
                else AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.signInTitle))
                    .setMessage(getString(R.string.wrongSignInText))
                    .setPositiveButton(getString(R.string.confirmButtonText)) { _, _ ->}
                    .create().show()
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            delay(DELAY_BEFORE_TRY_AUTO_SIGN_IN)
            withContext(Dispatchers.Main) {
                try {
                    viewModel.continueIfUserSignInBefore()
                } catch (e: Exception) {
                    Log.e(TAG, e.message?:TAG)
                }
            }
        }

        return view
    }

    private fun validate(login: String, password: String): Boolean {
        if (!toastValidateAnyFiled(
                context = requireContext(),
                value = login,
                reflectFunction = ::validateMailPattern,
                badReflectString = getString(R.string.wrongLoginFormatText)
            )
        ) return false
        else if (!toastValidateAnyFiled(
                context = requireContext(),
                value = password,
                reflectFunction = ::validatePasswordPattern,
                badReflectString = getString(R.string.wrongPasswordFormatText)
            )
        ) return false
        return true
    }

    companion object {
        private const val TAG = "SIGN_IN_FRAGMENT"
        private const val DELAY_BEFORE_TRY_AUTO_SIGN_IN = 1000L
    }
}