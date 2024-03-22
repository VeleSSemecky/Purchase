//[data](../../../index.md)/[com.veles.purchase.data.repository.setting](../index.md)/[SettingRepositoryImpl](index.md)

# SettingRepositoryImpl

[androidJvm]\
@Singleton

class [SettingRepositoryImpl](index.md)@Injectconstructor(settingsDataStore: [SettingsDataStore](../../com.veles.purchase.data.local.store/-settings-data-store/index.md)) : [SettingRepository](../../../../domain/domain/com.veles.purchase.domain.repository.setting/-setting-repository/index.md)

## Constructors

| | |
|---|---|
| [SettingRepositoryImpl](-setting-repository-impl.md) | [androidJvm]<br>@Inject<br>constructor(settingsDataStore: [SettingsDataStore](../../com.veles.purchase.data.local.store/-settings-data-store/index.md)) |

## Functions

| Name | Summary |
|---|---|
| [getFlowSettingsPurchase](get-flow-settings-purchase.md) | [androidJvm]<br>open override fun [getFlowSettingsPurchase](get-flow-settings-purchase.md)(): Flow&lt;[PurchaseSetting](../../../../domain/domain/com.veles.purchase.domain.model.setting/-purchase-setting/index.md)&gt; |
| [saveSettingsPurchase](save-settings-purchase.md) | [androidJvm]<br>open suspend override fun [saveSettingsPurchase](save-settings-purchase.md)(purchaseSetting: [PurchaseSetting](../../../../domain/domain/com.veles.purchase.domain.model.setting/-purchase-setting/index.md)) |
