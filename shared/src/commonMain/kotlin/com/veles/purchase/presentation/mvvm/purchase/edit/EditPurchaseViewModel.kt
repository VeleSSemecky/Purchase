package com.veles.purchase.presentation.mvvm.purchase.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veles.purchase.domain.model.history.HistoryType
import com.veles.purchase.domain.model.purchase.PhotoStatus
import com.veles.purchase.domain.model.purchase.PurchaseCategoryModel
import com.veles.purchase.domain.model.purchase.PurchaseModel
import com.veles.purchase.domain.model.purchase.PurchasePhotoModel
import com.veles.purchase.domain.model.scanner.PendingPhotoStore
import com.veles.purchase.domain.repository.storage.DeletePurchasePhotoRepository
import com.veles.purchase.domain.usecase.collection.GetCollectionPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.GetPurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.SavePurchaseUseCase
import com.veles.purchase.domain.usecase.purchase.UploadPurchasePhotosUseCase
import com.veles.purchase.domain.utill.createPrimaryIDKey
import com.veles.purchase.platform.logger.AppLogger
import com.veles.purchase.presentation.model.UiEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * UI State for Purchase Edit Screen
 */
data class PurchaseEditUiState(
    val purchase: PurchaseModel = PurchaseModel.EMPTY,
    val categories: List<PurchaseCategoryModel> = emptyList(),
    val progress: EditPurchaseViewModel.ProgressState = EditPurchaseViewModel.ProgressState.End,
    val isNewPurchase: Boolean = false,
    val photosToDelete: List<PurchasePhotoModel> = emptyList()
)

/**
 * ViewModel for Purchase Edit/Add Screen
 * Migrated from presentation module - original name: EditPurchaseViewModel
 */
class EditPurchaseViewModel(
    private val collectionId: String,
    private val purchaseId: String,
    private val getPurchaseUseCase: GetPurchaseUseCase,
    private val savePurchaseUseCase: SavePurchaseUseCase,
    private val getCollectionPurchaseUseCase: GetCollectionPurchaseUseCase,
    private val uploadPurchasePhotosUseCase: UploadPurchasePhotosUseCase,
    private val deletePurchasePhotoRepository: DeletePurchasePhotoRepository,
    private val pendingPhotoStore: PendingPhotoStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(PurchaseEditUiState(isNewPurchase = purchaseId.isEmpty()))
    val uiState: StateFlow<PurchaseEditUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<UiEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<UiEvent> = _events.asSharedFlow()

    init {
        loadPurchase()
        loadCategories()
        // If opened from scanner, attach the captured photo automatically
        pendingPhotoStore.consume()?.let { bytes -> onPhotoAdded(bytes) }
    }

    @OptIn(ExperimentalUuidApi::class)
    private fun loadPurchase() {
        viewModelScope.launch {
            _uiState.update { it.copy(progress = ProgressState.Start) }
            try {
                if (purchaseId.isNotEmpty()) {
                    val purchase = getPurchaseUseCase(collectionId, purchaseId)
                    if (purchase != null) {
                        _uiState.update { it.copy(purchase = purchase) }
                    }
                } else {
                    val newPurchase = PurchaseModel(
                        createId = Uuid.random().toString().uppercase(),
                        text = "",
                        count = "1",
                        isChecked = false,
                        price = "",
                        userList = emptyList(),
                        listImage = emptyList(),
                        purchaseCategoryModel = null
                    )
                    _uiState.update { it.copy(purchase = newPurchase) }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load purchase"))
            } finally {
                _uiState.update { it.copy(progress = ProgressState.End) }
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            try {
                val collection = getCollectionPurchaseUseCase(collectionId)
                if (collection != null) {
                    _uiState.update { it.copy(categories = collection.categoryModels) }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _events.emit(UiEvent.ShowError(e.message ?: "Failed to load categories"))
            }
        }
    }

    fun onTitleChange(title: String) {
        _uiState.update { it.copy(purchase = it.purchase.copy(text = title)) }
    }

    fun onPriceChange(price: String) {
        _uiState.update { it.copy(purchase = it.purchase.copy(price = price)) }
    }

    fun onCommentChange(comment: String) {
        _uiState.update { it.copy(purchase = it.purchase.copy(count = comment)) }
    }

    fun onCheckedChange(isChecked: Boolean) {
        _uiState.update { it.copy(purchase = it.purchase.copy(isChecked = isChecked)) }
    }

    fun onCategorySelected(category: PurchaseCategoryModel?) {
        _uiState.update { it.copy(purchase = it.purchase.copy(purchaseCategoryModel = category)) }
    }

    fun onPhotoAdded(bytes: ByteArray) {
        val photo = PurchasePhotoModel(
            purchaseId = _uiState.value.purchase.createId,
            purchasePhotoId = createPrimaryIDKey(),
            purchasePhotoUri = "",
            status = PhotoStatus.LOCAL,
            bytes = bytes
        )
        _uiState.update {
            it.copy(purchase = it.purchase.copy(listImage = it.purchase.listImage + photo))
        }
    }

    fun onPhotoDeleted(photo: PurchasePhotoModel) {
        _uiState.update {
            it.copy(
                purchase = it.purchase.copy(listImage = it.purchase.listImage - photo),
                photosToDelete = it.photosToDelete + photo
            )
        }
    }

    fun onSaveClicked() {
        val currentPurchase = _uiState.value.purchase
        if (currentPurchase.text.isBlank()) return

        viewModelScope.launch {
            _uiState.update { it.copy(progress = ProgressState.Start) }
            try {
                // 1. Delete removed photos from Cloudinary
                _uiState.value.photosToDelete.forEach { deletePurchasePhotoRepository.deletePhoto(it) }

                // 2. Upload new LOCAL photos and get download URLs
                val uploadedPhotos = uploadPurchasePhotosUseCase(currentPurchase.listImage)
                    .getOrElse {
                        _events.emit(UiEvent.ShowError(it.message ?: "Photo upload failed"))
                        return@launch
                    }

                // 3. Save to Firestore
                val historyType = if (_uiState.value.isNewPurchase) HistoryType.ADD else HistoryType.CHANGE
                savePurchaseUseCase(currentPurchase.copy(listImage = uploadedPhotos), collectionId, historyType)
                    .onSuccess {
                        _uiState.update { it.copy(progress = ProgressState.End, photosToDelete = emptyList()) }
                        _events.emit(UiEvent.NavigateBack)
                    }
                    .onFailure {
                        AppLogger.e("EditPurchaseViewModel", "Save failed: ${it.message}", it)
                        _uiState.update { it.copy(progress = ProgressState.End) }
                        _events.emit(UiEvent.ShowError(it.message ?: "Failed to save purchase"))
                    }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                AppLogger.e("EditPurchaseViewModel", "Save failed: ${e.message}", e)
                _uiState.update { it.copy(progress = ProgressState.End) }
                _events.emit(UiEvent.ShowError(e.message ?: "Unexpected error"))
            }
        }
    }

    enum class ProgressState {
        Start,
        End
    }
}
