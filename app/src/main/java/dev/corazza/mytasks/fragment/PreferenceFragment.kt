package dev.corazza.mytasks.fragment

import android.os.Bundle
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import dev.corazza.mytasks.R

class PreferenceFragment : PreferenceFragmentCompat() {
  override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
    setPreferencesFromResource(R.xml.preferences, rootKey)
    val dailyNotification = findPreference<SwitchPreference>("daily_notification")

    dailyNotification?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener {_, newValue ->

      if(newValue.toString().toBoolean()) {
        Firebase.messaging.subscribeToTopic("daily_notification").addOnCompleteListener {
          Log.e("fcm", "Subscription")
        }
        } else {
          Firebase.messaging.unsubscribeFromTopic("daily_notification").addOnCompleteListener {
            Log.e("fcm", "Unsubscription")
          }
      }
      true

    }

  }
}