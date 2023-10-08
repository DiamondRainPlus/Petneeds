package com.yagmurceliksoy.petneeds.data.model

data class AddToCartRequest(
    val userId: String,
    val productId: Int
)