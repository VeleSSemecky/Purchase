package com.veles.purchase.data.extensions

import dev.gitlive.firebase.auth.FirebaseUser
import com.veles.purchase.data.entity.user.UserDto
import com.veles.purchase.domain.utill.emptyString

/**
 * Convert Firebase User to UserDto
 */
fun FirebaseUser.toUserDto(fcmToken: String = emptyString()): UserDto = UserDto(
    uid = uid,
    providerId = providerId,
    displayName = displayName,
    email = email,
    phoneNumber = phoneNumber,
    fcmToken = fcmToken,
    photoUrl = photoURL
)

