package com.veles.purchase.presentation.base.mvvm.navigation

import androidx.navigation.NavController

interface Router {
    operator fun invoke(): NavController
}
