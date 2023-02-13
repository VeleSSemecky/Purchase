package com.veles.purchase.presentation.di.module

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.veles.purchase.presentation.R
import com.veles.purchase.presentation.presentation.activity.MainActivity
import dagger.Module
import dagger.Provides

@Module
object NavControllerModule {

    @Provides
    fun provideNavController(activity: MainActivity): NavController {
        return activity.findNavController(R.id.fragment_container)
    }
}
