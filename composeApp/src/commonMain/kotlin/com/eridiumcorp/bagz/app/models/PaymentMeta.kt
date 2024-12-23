package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractString

data class PaymentMeta(
    val referenceNumber: String? = null,
    val ppdId: String? = null,
    val payee: String? = null,
    val byOrderOf: String? = null,
    val payer: String? = null,
    val paymentMethod: String? = null,
    val paymentProcessor: String? = null,
    val reason: String? = null,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): PaymentMeta {
            return PaymentMeta(
                referenceNumber = map.extractString("reference_number"),
                ppdId = map.extractString("ppd_id"),
                payee = map.extractString("payee"),
                byOrderOf = map.extractString("by_order_of"),
                payer = map.extractString("payer"),
                paymentMethod = map.extractString("payment_method"),
                paymentProcessor = map.extractString("payment_processor"),
                reason = map.extractString("reason")
            )
        }
    }
}
