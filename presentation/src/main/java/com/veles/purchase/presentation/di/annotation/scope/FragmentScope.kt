package com.veles.purchase.presentation.di.annotation.scope

import javax.inject.Scope

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER
)
@Scope
@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
annotation class FragmentScope
