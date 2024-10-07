package ru.mirea.sign_up.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import org.koin.android.ext.android.inject
import ru.mirea.core.alertCheckConnection
import ru.mirea.core.toastValidateAnyFiled
import ru.mirea.core.validateMailPattern
import ru.mirea.core.validatePasswordPattern
import ru.mirea.sign_up.R

class SignUpFragment : Fragment() {
    private val viewModel by inject<SignUpViewModel>()

    private lateinit var view: View

    private lateinit var etLogin: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPasswordConfirm: EditText
    private lateinit var btnSignUp: Button

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = inflater.inflate(R.layout.fragment_sign_up, container, false)

        etLogin = view.findViewById(R.id.etLogin)
        etPassword = view.findViewById(R.id.etPassword)
        etPasswordConfirm = view.findViewById(R.id.etPasswordConfirm)
        btnSignUp = view.findViewById(R.id.btnSignUp)

        progressBar = view.findViewById(R.id.progress_circular)

        btnSignUp.setOnClickListener {

            val login = etLogin.text.toString()
            val password = etPassword.text.toString()
            val passwordConfirm = etPasswordConfirm.text.toString()

            if (!validate(login, password, passwordConfirm)) return@setOnClickListener
            if (!alertCheckConnection(requireContext())) return@setOnClickListener

            progressBar.visibility = ProgressBar.VISIBLE
            btnSignUp.isEnabled = false
            viewModel.signUpInAuth(login, password) { authInAuthDBResult ->
                progressBar.visibility = ProgressBar.GONE
                btnSignUp.isEnabled = true

                if (authInAuthDBResult)
                    AlertDialog.Builder(requireContext())
                        .setTitle(R.string.RegistrationTitle)
                        .setMessage(R.string.suucessRegistrationText)
                        .setPositiveButton(ru.mirea.core.R.string.OK) { _, _ -> }
                        .setOnDismissListener { viewModel.goBack() }
                        .create().show()
                else
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.RegistrationTitle))
                        .setMessage(getString(R.string.registrationErrorMessage))
                        .setPositiveButton(getString(ru.mirea.core.R.string.OK)) { _, _ -> }
                        .create().show()


            }
        }

        return view
    }

    private fun validate(
        login: String,
        password: String,
        passwordConfirm: String,
    ): Boolean {
        if (!toastValidateAnyFiled(
                context = requireContext(),
                value = login,
                reflectFunction = ::validateMailPattern,
                badReflectString = getString(R.string.badEmail)
            )) return false
        else if (!toastValidateAnyFiled(
                context = requireContext(),
                value = password,
                reflectFunction = ::validatePasswordPattern,
                badReflectString = getString(R.string.badPassword)
            )) return false
        else if (!toastValidateAnyFiled(
                context = requireContext(),
                value = passwordConfirm,
                reflectFunction =  { passwordConf: String -> password == passwordConf },
                badReflectString = getString(R.string.badConfirm)
            )) return false
        return true
    }

    companion object {
        private const val TAG = "SIGN_UP_FRAGMENT"
    }

}