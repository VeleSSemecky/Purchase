package com.veles.purchase.presentation.base.mvvm.navigation

import androidx.navigation.NavController
import java.lang.ref.WeakReference

/**
 * Router implementation converted from Dagger to Koin
 */
class RouterImpl : Router {

    private var navController: WeakReference<NavController> = WeakReference(null)

    override operator fun invoke(): NavController =
        navController.get() ?: throw IllegalStateException("NavController is null")

    override fun setNavController(navController: NavController) {
        this.navController = WeakReference(navController)
    }
}
