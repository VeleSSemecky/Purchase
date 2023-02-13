package com.veles.purchase.presentation.base.mvvm.fragment

import android.view.Menu
import android.view.MenuInflater
import androidx.core.view.MenuProvider

interface MenuItemSelected : MenuProvider {

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {}
}
