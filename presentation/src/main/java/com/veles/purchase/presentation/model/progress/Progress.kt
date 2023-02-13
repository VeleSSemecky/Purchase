package com.veles.purchase.presentation.model.progress

sealed class Progress {
    object End : Progress()
    object Start : Progress()
}
