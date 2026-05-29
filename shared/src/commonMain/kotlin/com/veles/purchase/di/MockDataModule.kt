package com.veles.purchase.di

import org.koin.dsl.module

/**
 * Mock Data Module - DEPRECATED
 *
 * Phase 6 Complete:
 * ✅ All repositories migrated to real implementations:
 * - PurchaseRepository → Firebase
 * - CollectionPurchaseRepository → Firebase
 * - CollectionRepository → Firebase
 * - AuthWithGoogle → Firebase
 * - User repos → Firebase
 * - SkuRepository → Room
 * - SkuPhotoRepository → Room
 * - HistoryRepository → Room
 * - SettingRepository → In-memory (TODO: DataStore KMP)
 *
 * This module is now empty and kept for backward compatibility.
 * All repositories are now provided by repositoryModule.
 */
val mockDataModule = module {
    // No mock dependencies needed anymore!
    // All repositories are real implementations now
}
