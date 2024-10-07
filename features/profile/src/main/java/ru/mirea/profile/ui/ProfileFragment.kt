package ru.mirea.profile.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import org.koin.android.ext.android.inject
import ru.mirea.core.checkInternet
import ru.mirea.core.switchTheme
import ru.mirea.core.toastValidateAnyFiled
import ru.mirea.profile.R
import ru.mirea.core.validatePasswordPattern

class ProfileFragment : Fragment() {
    private val viewModel by inject<ProfileViewModel>()

    private lateinit var view: View

    private lateinit var btnChangeUser: Button
    private lateinit var btnChangePassword: ImageView
    private lateinit var btnThemeLight: Button
    private lateinit var btnThemeDark: Button
    private lateinit var btnCatalog: ImageView
    private lateinit var btnBasket: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        view = inflater.inflate(R.layout.fragment_profile, container, false)

        btnChangeUser = view.findViewById(R.id.btnChangeUser)
        btnChangePassword = view.findViewById(R.id.btnEditPassword)
        btnThemeLight = view.findViewById(R.id.btnLight)
        btnThemeDark = view.findViewById(R.id.btnDark)
        btnCatalog = view.findViewById(R.id.ivCatalog)
        btnBasket = view.findViewById(R.id.ivBasket)

        btnCatalog.setOnClickListener { viewModel.lunchCatalog() }
        btnBasket.setOnClickListener { viewModel.lunchBasket() }
        btnChangeUser.setOnClickListener { viewModel.logOut() }

        btnChangePassword.setOnClickListener {
            buildAlert(
                getString(R.string.editPasswordTitle),
                getString(R.string.newPasswordHint),
                getString(R.string.confirmText),
                { newPassword -> setNewPassword(newPassword) },
                getString(R.string.dismissText),
                { _ -> }
            )
        }

        btnThemeLight.setOnClickListener { switchTheme(requireContext(), false) }
        btnThemeDark.setOnClickListener { switchTheme(requireContext(), true) }

        return view
    }

    private fun getUser(callback: (FirebaseUser)->Unit) {
        checkInternet(requireContext())
        viewModel.getUser(callback)
    }

    private fun setNewPassword(newPassword: String) {
        if (toastValidateAnyFiled(
                requireContext(),
                newPassword,
                ::validatePasswordPattern,
                getString(
                    R.string.wrongPasswordText
                )
            )) viewModel.setNewPassword(newPassword) {
            if (it) {
                Toast.makeText(requireContext(),
                    getString(R.string.successChangePassword), Toast.LENGTH_LONG).show()
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle(getString(R.string.editPasswordTitle))
                    .setMessage(getString(R.string.changePasswordErrorText))
                    .setPositiveButton(getString(ru.mirea.core.R.string.OK)) { _, _ -> }
                    .create().show()
            }
        }
    }

    private fun buildAlert(
        title: String,
        hint: String,
        positiveText: String,
        actionOnPositive: (String) -> Unit,
        negativeText: String,
        actionOnNegative: (String) -> Unit,
    ) {
        val dialogView = layoutInflater.inflate(R.layout.set_new_data_in_filed, null)
        val dialogTitle = dialogView.findViewById<TextView>(R.id.title)
        val dialogEdit = dialogView.findViewById<EditText>(R.id.edit)

        dialogTitle.text = title
        dialogEdit.hint = hint

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton(positiveText) { _, _ -> actionOnPositive(dialogEdit.text.toString()) }
            .setNegativeButton(negativeText) { _, _ -> actionOnNegative(dialogEdit.text.toString()) }
            .create().show()
    }

    companion object {
        private const val TAG = "PROFILE_FRAGMENT"
    }
}