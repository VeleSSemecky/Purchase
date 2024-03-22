//[presentation](../../index.md)/[com.veles.purchase.presentation.model.event](index.md)

# Package-level declarations

## Types

| Name | Summary |
|---|---|
| [CurrencyEvent](-currency-event/index.md) | [androidJvm]<br>data class [CurrencyEvent](-currency-event/index.md)(val currencyCode: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)) : [Event](../com.veles.purchase.presentation.data.bus/-event/index.md) |
| [DialogEvent](-dialog-event/index.md) | [androidJvm]<br>class [DialogEvent](-dialog-event/index.md) : [Event](../com.veles.purchase.presentation.data.bus/-event/index.md) |
| [MonthEvent](-month-event/index.md) | [androidJvm]<br>data class [MonthEvent](-month-event/index.md)(val monthOrAll: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = zeroInt()) : [Event](../com.veles.purchase.presentation.data.bus/-event/index.md) |
| [PurchasePhotoDeleteEvent](-purchase-photo-delete-event/index.md) | [androidJvm]<br>data class [PurchasePhotoDeleteEvent](-purchase-photo-delete-event/index.md)(val purchasePhotoModel: [PurchasePhotoModelUI](../com.veles.purchase.presentation.model.purchase/-purchase-photo-model-u-i/index.md)) : [Event](../com.veles.purchase.presentation.data.bus/-event/index.md) |
| [SkuPhotoDeleteEvent](-sku-photo-delete-event/index.md) | [androidJvm]<br>data class [SkuPhotoDeleteEvent](-sku-photo-delete-event/index.md)(val skuPhotoEntity: [SkuPhotoEntity](../../../data/data/com.veles.purchase.data.room.table/-sku-photo-entity/index.md)) : [Event](../com.veles.purchase.presentation.data.bus/-event/index.md) |
| [SortPurchaseEvent](-sort-purchase-event/index.md) | [androidJvm]<br>data class [SortPurchaseEvent](-sort-purchase-event/index.md)(val sortPurchase: [SortPurchase](../com.veles.purchase.presentation.model.sort/-sort-purchase/index.md)) : [Event](../com.veles.purchase.presentation.data.bus/-event/index.md) |
| [YearEvent](-year-event/index.md) | [androidJvm]<br>data class [YearEvent](-year-event/index.md)(val year: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) : [Event](../com.veles.purchase.presentation.data.bus/-event/index.md) |
