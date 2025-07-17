//package com.veles.purchase.presentation.test
//
//import org.junit.After
//import org.junit.Before
//import org.junit.Test
//import org.koin.core.context.startKoin
//import org.koin.core.context.stopKoin
//import org.koin.test.KoinTest
//import org.koin.test.inject
//import org.mockk.every
//import org.mockk.verify
//import com.veles.purchase.presentation.di.module.testPresentationModules
//import com.veles.purchase.presentation.presentation.mvvm.purchase.login.LoginViewModel
//import com.veles.purchase.domain.repository.PurchaseRepository
//import kotlinx.coroutines.flow.flowOf
//
///**
// * Example test class showing how Koin simplifies testing
// */
//class LoginViewModelTest : KoinTest {
//
//    // Inject dependencies from Koin (mocked in test modules)
//    private val mockRepository: PurchaseRepository by inject()
//    private val loginViewModel: LoginViewModel by inject()
//
//    @Before
//    fun setUp() {
//        startKoin {
//            modules(testPresentationModules)
//        }
//    }
//
//    @After
//    fun tearDown() {
//        stopKoin()
//    }
//
//    @Test
//    fun `when login is called, should interact with repository`() {
//        // Given
//        every { mockRepository.getAllPurchases() } returns flowOf(emptyList())
//
//        // When
//        // loginViewModel.login()
//
//        // Then
//        // verify { mockRepository.getAllPurchases() }
//
//        // Koin makes testing much simpler than Dagger!
//        // No need to create complex test components or modules
//    }
//}
