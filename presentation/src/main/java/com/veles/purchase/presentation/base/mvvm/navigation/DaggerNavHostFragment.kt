package com.veles.purchase.presentation.base.mvvm.navigation

import android.content.Context
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import org.koin.android.ext.android.inject

/**
 * NavHostFragment converted from Dagger to Koin
 */
class KoinNavHostFragment : NavHostFragment() {

    private val router: Router by inject()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.setNavController(navController)
    }
}
