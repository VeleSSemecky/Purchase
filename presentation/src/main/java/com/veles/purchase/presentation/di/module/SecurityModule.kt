package com.veles.purchase.presentation.di.module

import com.veles.purchase.data.local.cryptography.AesCipherProvider.Companion.KEY_NAME
import com.veles.purchase.data.local.cryptography.AesCipherProvider.Companion.KEY_STORE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import java.security.KeyStore
import javax.inject.Named

@Module
object SecurityModule {

    private const val ANDROID_KEY_STORE_TYPE = "AndroidKeyStore"
    private const val SIMPLE_DATA_KEY_NAME = "SimpleDataKeys"

    @Provides
    fun provideKeyStore(): KeyStore =
        KeyStore.getInstance(ANDROID_KEY_STORE_TYPE).apply { load(null) }

    @Provides
    @Named(KEY_NAME)
    fun providesKeyName(): String = SIMPLE_DATA_KEY_NAME

    @Provides
    @Named(KEY_STORE_NAME)
    fun providesKeyStoreName(): String = ANDROID_KEY_STORE_TYPE

//    @Module
//    interface Declarations {
//
//        @Binds
//        fun bindsCipherProvider(impl: AesCipherProvider): CipherProvider
//
//        @Binds
//        fun bindsCrypto(impl: CryptoImpl): Crypto
//    }
}
