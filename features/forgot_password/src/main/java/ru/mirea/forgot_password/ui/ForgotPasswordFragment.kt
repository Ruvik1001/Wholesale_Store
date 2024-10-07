package ru.mirea.forgot_password.ui

import android.app.AlertDialog
import androidx.fragment.app.viewModels
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
import ru.mirea.core.alertValidateAnyFiled
import ru.mirea.forgot_password.R
import ru.mirea.core.validateMailPattern

class ForgotPasswordFragment : Fragment() {

    private val viewModel by inject<ForgotPasswordViewModel>()

    private lateinit var view: View

    private lateinit var etLogin: EditText
    private lateinit var btnResetPassword: Button

    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        etLogin = view.findViewById(R.id.etLogin)
        btnResetPassword = view.findViewById(R.id.btnResetPassword)

        progressBar = view.findViewById(R.id.progress_circular)

        btnResetPassword.setOnClickListener {
            val login = etLogin.text.toString()

            if (!alertCheckConnection(requireContext())) return@setOnClickListener
            if (!alertValidateAnyFiled(
                    context = requireContext(),
                    value = login,
                    reflectFunction = ::validateMailPattern,
                    badReflectString = getString(R.string.badEmail)
                )) return@setOnClickListener

            progressBar.visibility = ProgressBar.VISIBLE
            btnResetPassword.isEnabled = false
            viewModel.resetPassword(login) {
                progressBar.visibility = ProgressBar.GONE
                btnResetPassword.isEnabled = true

                if (!it) {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.resetTitle))
                        .setMessage(getString(R.string.resetErrorMessage))
                        .setPositiveButton(ru.mirea.core.R.string.OK) { _, _ -> }
                        .create().show()
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.resetTitle))
                        .setMessage(getString(R.string.resetSuccessMessage))
                        .setPositiveButton(ru.mirea.core.R.string.OK) { _, _ -> }
                        .setOnDismissListener { viewModel.goBack() }
                        .create().show()
                }
            }
        }

        return view
    }
}