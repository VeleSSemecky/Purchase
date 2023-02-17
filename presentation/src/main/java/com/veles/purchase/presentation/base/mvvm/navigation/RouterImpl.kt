package com.veles.purchase.presentation.base.mvvm.navigation

import androidx.navigation.NavController
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RouterImpl @Inject constructor() : Router {

    private var navController: WeakReference<NavController> = WeakReference(null)

    override operator fun invoke(): NavController =
        navController.get() ?: throw IllegalStateException("NavController is null")

    fun setNavController(navController: NavController) {
        this.navController = WeakReference(navController)
    }
}
