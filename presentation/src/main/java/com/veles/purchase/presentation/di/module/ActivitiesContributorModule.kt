package com.veles.purchase.presentation.di.module

import android.content.ContentResolver
import android.content.Context
import com.veles.purchase.presentation.presentation.activity.MainActivity
import com.veles.purchase.presentation.presentation.mvvm.pip.PIP
import com.veles.purchase.presentation.presentation.mvvm.pip.PIPModule
import com.veles.purchase.presentation.presentation.mvvm.pip.PIPScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
interface ActivitiesContributorModule {

    @ContributesAndroidInjector(
        modules = [
            FragmentsContributorModule::class,
            NavControllerModule::class
        ]
    )
    fun mainActivity(): MainActivity

    @PIPScope
    @ContributesAndroidInjector(modules = [PIPModule::class])
    fun pipActivity(): PIP

    companion object {

        @Provides
        fun provideContentResolver(context: Context): ContentResolver {
            return context.contentResolver
        }
    }
}
