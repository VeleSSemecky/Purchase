package com.veles.purchase.domain.repository.sku

import com.veles.purchase.domain.model.SkuModel
import com.veles.purchase.domain.model.SkuPhotoModel
import com.veles.purchase.domain.model.SkuSumMonthModel
import com.veles.purchase.domain.utill.createPrimaryIDKey
import kotlinx.coroutines.delay

class MockSkuRepository : SkuRepository {

    private val mockSkus = mutableListOf(
        SkuModel(
            skuId = createPrimaryIDKey(),
            skuName = "Молоко Organic",
            skuComment = "Ферма 'Зелені луги'",
            skuPrice = "45.50",
            skuCurrencyCode = "UAH"
        ),
        SkuModel(
            skuId = createPrimaryIDKey(),
            skuName = "Хліб Бородинський",
            skuComment = "Пекарня на розі",
            skuPrice = "25.00",
            skuCurrencyCode = "UAH"
        ),
        SkuModel(
            skuId = createPrimaryIDKey(),
            skuName = "Кава Lavazza",
            skuComment = "500г зерновий",
            skuPrice = "380.00",
            skuCurrencyCode = "UAH"
        )
    )

    override suspend fun getSkuModel(skuId: String): SkuModel? {
        delay(200)
        return mockSkus.find { it.skuId == skuId }
    }

    override suspend fun getSkuEntityList(): List<SkuModel> {
        delay(300)
        return mockSkus.toList()
    }

    override suspend fun insert(skuModel: SkuModel, skuPhotoModelList: List<SkuPhotoModel>) {
        delay(250)
        val index = mockSkus.indexOfFirst { it.skuId == skuModel.skuId }
        if (index != -1) {
            mockSkus[index] = skuModel
        } else {
            mockSkus.add(skuModel)
        }
    }

    override suspend fun delete(id: String) {
        delay(200)
        mockSkus.removeAll { it.skuId == id }
    }

    override suspend fun getSkuSumMonthList(year: Int, month: Int): List<SkuSumMonthModel> {
        delay(300)
        return listOf(
            SkuSumMonthModel(
                skuName = "Молоко",
                sum = "273.00",
                currencyCode = "UAH"
            ),
            SkuSumMonthModel(
                skuName = "Хліб",
                sum = "150.00",
                currencyCode = "UAH"
            ),
            SkuSumMonthModel(
                skuName = "Кава",
                sum = "760.00",
                currencyCode = "UAH"
            )
        )
    }
}

