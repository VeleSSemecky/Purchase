package com.veles.purchase.domain.repository.collection

import com.veles.purchase.domain.model.purchase.PurchaseCollectionModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository for managing purchase collections
 *
 * A collection represents a group of purchases, like "Groceries", "Monthly Shopping", etc.
 * Each collection can have multiple members collaborating on the same purchase list.
 */
interface CollectionRepository {

    /**
     * Get all collections for the current user
     * @return Flow of list of collections
     */
    fun getCollections(): Flow<List<PurchaseCollectionModel>>

    /**
     * Get a specific collection by ID
     * @param collectionId Collection ID
     * @return The collection or null if not found
     */
    suspend fun getCollection(collectionId: String): PurchaseCollectionModel?

    /**
     * Create or update a collection
     * @param collection Collection to save
     */
    suspend fun saveCollection(collection: PurchaseCollectionModel)

    /**
     * Delete a collection
     * @param collection Collection to delete
     */
    suspend fun deleteCollection(collection: PurchaseCollectionModel)
}
