package com.curidev.ayni.feature_payment.domain


data class Card (
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val name: String,
    val address: String,
    val country: String,
    val city: String,
    val postalCode: String
)
