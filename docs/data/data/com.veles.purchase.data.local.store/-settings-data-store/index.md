//[data](../../../index.md)/[com.veles.purchase.data.local.store](../index.md)/[SettingsDataStore](index.md)

# SettingsDataStore

[androidJvm]\
@Singleton

class [SettingsDataStore](index.md)@Injectconstructor(dataStore: [DataStore](https://developer.android.com/reference/kotlin/androidx/datastore/core/DataStore.html)&lt;[Preferences](https://developer.android.com/reference/kotlin/androidx/datastore/preferences/core/Preferences.html)&gt;)

## Constructors

| | |
|---|---|
| [SettingsDataStore](-settings-data-store.md) | [androidJvm]<br>@Inject<br>constructor(dataStore: [DataStore](https://developer.android.com/reference/kotlin/androidx/datastore/core/DataStore.html)&lt;[Preferences](https://developer.android.com/reference/kotlin/androidx/datastore/preferences/core/Preferences.html)&gt;) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [flowPurchaseSetting](flow-purchase-setting.md) | [androidJvm]<br>val [flowPurchaseSetting](flow-purchase-setting.md): Flow&lt;[PurchaseSetting](../../../../domain/domain/com.veles.purchase.domain.model.setting/-purchase-setting/index.md)&gt; |

## Functions

| Name | Summary |
|---|---|
| [saveSettingsPurchase](save-settings-purchase.md) | [androidJvm]<br>suspend fun [saveSettingsPurchase](save-settings-purchase.md)(purchaseSetting: [PurchaseSetting](../../../../domain/domain/com.veles.purchase.domain.model.setting/-purchase-setting/index.md)) |
