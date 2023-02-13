package com.veles.purchase.presentation.presentation.activity

import android.os.Bundle
import androidx.annotation.CallSuper
import com.veles.purchase.presentation.R
import dagger.android.support.DaggerAppCompatActivity

class MainActivity : DaggerAppCompatActivity(R.layout.main_activity) {

    @CallSuper
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
