package com.veles.purchase.presentation.base.mvvm.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import com.veles.purchase.presentation.base.mvvm.viewmodel.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @CallSuper
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onViewReady(savedInstanceState)
    }

    protected open fun onViewReady(savedInstanceState: Bundle?) {}
}
