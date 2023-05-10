package com.veles.purchase.presentation.presentation.mvvm.purchase.biometric

// import com.veles.purchase.presentation.presentation.mvvm.purchase.biometric.BiometricPromptUtils.CIPHERTEXT_WRAPPER
// import com.veles.purchase.presentation.presentation.mvvm.purchase.biometric.BiometricPromptUtils.SHARED_PREFS_FILENAME
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.auth.authenticateWithClass3Biometrics
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.veles.purchase.domain.model.cryptography.CiphertextWrapper
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.base.mvvm.fragment.BaseFragment
import com.veles.purchase.presentation.presentation.compose.MyTheme
import kotlinx.coroutines.launch

class BiometricComposeFragment : BaseFragment() {

    private val viewModel: BiometricComposeViewModel by viewModels { viewModelFactory }

//    private val cryptographyManager = CryptographyManager()
//    private val ciphertextWrapper
//        get() = cryptographyManager.getCiphertextWrapperFromSharedPrefs(
//            requireContext(),
//            SHARED_PREFS_FILENAME,
//            Context.MODE_PRIVATE,
//            CIPHERTEXT_WRAPPER
//        )a

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.compose_view,
            container,
            false
        ).apply {
            findViewById<ComposeView>(R.id.composeView).setContent {
                MyTheme {
                    val canAuthenticate = BiometricManager.from(requireContext()).canAuthenticate()
                    if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
//                        binding.useBiometrics.visibility = View.VISIBLE
//                        binding.useBiometrics.setOnClickListener {
                        when (CiphertextWrapper.EMPTY) {
                            viewModel.decryptionUseCase.getCiphertextWrapper() -> showBiometricPromptForEncryption()
                            else -> showBiometricPromptForDecryption()
                        }
                        //                            else {
//                                startActivity(Intent(this, EnableBiometricLoginActivity::class.java))
//                            }
                    }
                }
//                else {
//                        binding.useBiometrics.visibility = View.INVISIBLE
//                    }
//
//                    if (ciphertextWrapper == null) {
//                        setupForLoginWithPassword()
//                    }
//                }
            }
        }
    }

    private fun showBiometricPromptForDecryption() = lifecycleScope.launch {
        val auth = authenticateWithClass3Biometrics(
            crypto = viewModel.decryptionUseCase.getCryptoObjectForDecryption(
                viewModel.decryptionUseCase.getCiphertextWrapper().initializationVector
            )
                ?.let { BiometricPrompt.CryptoObject(it) },
            title = getString(R.string.prompt_info_title),
            negativeButtonText = getString(R.string.prompt_info_use_app_password),
            subtitle = getString(R.string.prompt_info_subtitle),
            description = getString(R.string.prompt_info_description),
            confirmationRequired = false
        )
        val decrypt = viewModel.decryptionUseCase.decryptData(auth.cryptoObject?.cipher)
        Log.i("showBiometricPrompt", "decrypt $decrypt")
//            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

//    private fun decryptServerTokenFromStorage(authResult: BiometricPrompt.AuthenticationResult) {
//        ciphertextWrapper?.let { textWrapper ->
//            ?.let {
//                val plaintext =
//                    cryptographyManager.decryptData(textWrapper.ciphertext, it)
// //                SampleAppUser.fakeToken = plaintext
//                // Now that you have the token, you can query server for everything else
//                // the only reason we call this fakeToken is because we didn't really get it from
//                // the server. In your case, you will have gotten it from the server the first time
//                // and therefore, it's a real token.
//
// //                updateApp(getString(R.string.already_signedin))
//            }
//        }
//    }

    private fun showBiometricPromptForEncryption() = lifecycleScope.launch {
        val canAuthenticate = BiometricManager.from(requireContext())
            .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val auth = authenticateWithClass3Biometrics(
                crypto = viewModel.encryptionUseCase.getCryptoObjectForEncryption()
                    ?.let { BiometricPrompt.CryptoObject(it) },
                title = getString(R.string.prompt_info_title),
                negativeButtonText = getString(R.string.prompt_info_use_app_password),
                subtitle = getString(R.string.prompt_info_subtitle),
                description = getString(R.string.prompt_info_description),
                confirmationRequired = false
            )
            val encrypt = java.util.UUID.randomUUID().toString()
            Log.i("showBiometricPrompt", "encrypt $encrypt")
            viewModel.encryptionUseCase.encryptAndSaveData(
                encrypt,
                auth.cryptoObject?.cipher
            )
        }
    }

//    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
//        authResult.cryptoObject?.cipher?.apply {
//            val encryptedServerTokenWrapper =
//                cryptographyManager.encryptData(java.util.UUID.randomUUID().toString(), this)
//            cryptographyManager.persistCiphertextWrapperToSharedPrefs(
//                encryptedServerTokenWrapper,
//                requireContext(),
//                SHARED_PREFS_FILENAME,
//                Context.MODE_PRIVATE,
//                CIPHERTEXT_WRAPPER
//            )
//        }
//    }
}
