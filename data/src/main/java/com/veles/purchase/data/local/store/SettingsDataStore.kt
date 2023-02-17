package com.veles.purchase.data.local.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.veles.purchase.domain.model.setting.PurchaseSetting
import com.veles.purchase.domain.model.setting.ShapeType
import com.veles.purchase.domain.model.setting.SizeType
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

@Singleton
class SettingsDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    val flowPurchaseSetting: Flow<PurchaseSetting> = dataStore.data.map { preferences ->
        val sizeType = preferences[SIZE_TYPE] ?: return@map null
        val typeShape = preferences[SHAPE_TYPE] ?: return@map null
        val isImage = preferences[IS_IMAGE] ?: return@map null
        val isSymmetry = preferences[IS_SYMMETRY] ?: return@map null
        val topStart = preferences[TOP_START] ?: return@map null
        val topEnd = preferences[TOP_END] ?: return@map null
        val bottomEnd = preferences[BOTTOM_END] ?: return@map null
        val bottomStart = preferences[BOTTOM_START] ?: return@map null
        PurchaseSetting(
            sizeType = SizeType.valueOf(sizeType),
            shapeType = ShapeType.valueOf(typeShape),
            isImage = isImage,
            topStart = topStart,
            topEnd = topEnd,
            bottomEnd = bottomEnd,
            bottomStart = bottomStart,
            isSymmetry = isSymmetry
        )
    }.filterNotNull()

    suspend fun saveSettingsPurchase(purchaseSetting: PurchaseSetting) {
        dataStore.edit { preferences ->
            preferences[SIZE_TYPE] = purchaseSetting.sizeType.name
            preferences[SHAPE_TYPE] = purchaseSetting.shapeType.name
            preferences[IS_IMAGE] = purchaseSetting.isImage
            preferences[TOP_START] = purchaseSetting.topStart
            preferences[TOP_END] = purchaseSetting.topEnd
            preferences[BOTTOM_END] = purchaseSetting.bottomEnd
            preferences[BOTTOM_START] = purchaseSetting.bottomStart
            preferences[IS_SYMMETRY] = purchaseSetting.isSymmetry
        }
    }

    companion object {
        private val SIZE_TYPE = stringPreferencesKey("SIZE_TYPE_KEY")
        private val SHAPE_TYPE = stringPreferencesKey("SHAPE_TYPE_KEY")
        private val IS_IMAGE = booleanPreferencesKey("IS_IMAGE_KEY")
        private val IS_SYMMETRY = booleanPreferencesKey("IS_SYMMETRY_KEY")
        private val TOP_START = floatPreferencesKey("TOP_START_KEY")
        private val TOP_END = floatPreferencesKey("TOP_END_KEY")
        private val BOTTOM_START = floatPreferencesKey("BOTTOM_START_KEY")
        private val BOTTOM_END = floatPreferencesKey("BOTTOM_END_KEY")
    }
}
