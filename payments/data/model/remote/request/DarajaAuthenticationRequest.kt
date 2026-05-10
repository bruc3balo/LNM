package com.example.lnm.payments.data.model.remote.request

import kotlinx.serialization.Serializable

@Serializable
data class DarajaAuthenticationRequest(
    val consumerKey: String,
    val consumerSecret: String,
)