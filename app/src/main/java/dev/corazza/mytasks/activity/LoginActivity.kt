package dev.corazza.mytasks.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dev.corazza.mytasks.R
import dev.corazza.mytasks.databinding.ActivityLoginBinding
import dev.corazza.mytasks.extension.hasValue
import dev.corazza.mytasks.extension.textValue
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        credentialManager = CredentialManager.create(this)

        if(auth.currentUser != null) {
            goToMain()
        }

        initComponents()
    }

    private fun initComponents() {
        binding.btLogin.setOnClickListener {
            if(validate()) {
                login()
            }
        }
        binding.btCreateAccount.setOnClickListener {
            if (validate()) {
                createAccount()
            }
        }

        binding.btGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        lifecycleScope.launch {
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .setAutoSelectEnabled(false)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    request = request,
                    context = this@LoginActivity
                )
                handleSignIn(result)

            } catch (e: GetCredentialException) {
                Log.e("Auth", "Error: ${e.message}")
                if (!e.message.toString().contains("User canceled")) {
                    Toast.makeText(this@LoginActivity, R.string.google_error, Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Auth", "Error: ${e.message}")
            }
        }
    }

    private fun handleSignIn(result: GetCredentialResponse) {
        val credential = result.credential
        if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            try {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
            } catch (e: Exception) {
                Log.e("Auth", "Error: ", e)
            }
        } else {
            Log.e("Auth", "Unknown error!")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMain()
                } else {
                    Toast.makeText(this, R.string.firebase_error, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun goToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun validate(): Boolean {
        var isValid = true
        binding.layoutEmail.error = null
        binding.layoutPassword.error = null

        if(!binding.etEmail.hasValue()) {
            isValid = false
            binding.layoutEmail.error= ContextCompat.getString(this, R.string.empty_email )
        }
        if(!binding.etPassword.hasValue()) {
            isValid = false
            binding.layoutPassword.error= ContextCompat.getString(this, R.string.empty_pass )
        }
        return isValid
    }

    private fun createAccount() {
        auth.createUserWithEmailAndPassword(binding.etEmail.textValue(), binding.etPassword.textValue())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("auth", "createUserWithEmail:success")
                } else {
                    val message = task.exception?.message ?: ContextCompat.getString(this, R.string.account_created_fail)
                    Log.e("auth", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        message,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun login() {
        auth.signInWithEmailAndPassword(binding.etEmail.textValue(), binding.etPassword.textValue())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                } else {
                    val message = task.exception?.message ?: ContextCompat.getString(this, R.string.login_fail)
                    Log.e("auth", "loginWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        message,
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}