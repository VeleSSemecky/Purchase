package com.example.shared.data.repository

import com.example.shared.data.local.dao.PurchaseDao
import com.example.shared.data.local.entity.toDomainModel
import com.example.shared.data.local.entity.toEntity
import com.example.shared.domain.model.purchase.PurchaseModel
import com.example.shared.domain.repository.PurchaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PurchaseRepositoryImpl(
    private val purchaseDao: PurchaseDao
) : PurchaseRepository {

    override fun getAllPurchases(): Flow<List<PurchaseModel>> {
        return purchaseDao.getAllPurchases().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getPurchaseById(id: String): PurchaseModel? {
        return purchaseDao.getPurchaseById(id)?.toDomainModel()
    }

    override fun getPurchasesByCollection(collectionId: String): Flow<List<PurchaseModel>> {
        return purchaseDao.getPurchasesByCollection(collectionId).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override fun getPurchasesByStatus(isChecked: Boolean): Flow<List<PurchaseModel>> {
        return purchaseDao.getPurchasesByStatus(isChecked).map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun insertPurchase(purchase: PurchaseModel) {
        purchaseDao.insertPurchase(purchase.toEntity())
    }

    override suspend fun insertPurchases(purchases: List<PurchaseModel>) {
        purchaseDao.insertPurchases(purchases.map { it.toEntity() })
    }

    override suspend fun updatePurchase(purchase: PurchaseModel) {
        purchaseDao.updatePurchase(purchase.toEntity())
    }

    override suspend fun deletePurchase(purchase: PurchaseModel) {
        purchaseDao.deletePurchase(purchase.toEntity())
    }

    override suspend fun deletePurchaseById(id: String) {
        purchaseDao.deletePurchaseById(id)
    }

    override suspend fun deletePurchasesByCollection(collectionId: String) {
        purchaseDao.deletePurchasesByCollection(collectionId)
    }

    override suspend fun deleteAllPurchases() {
        purchaseDao.deleteAllPurchases()
    }

    override suspend fun getPurchaseCount(): Int {
        return purchaseDao.getPurchaseCount()
    }

    override suspend fun getCheckedPurchaseCount(): Int {
        return purchaseDao.getCheckedPurchaseCount()
    }
}
