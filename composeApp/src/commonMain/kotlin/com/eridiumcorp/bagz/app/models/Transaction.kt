package com.eridiumcorp.bagz.app.models

import com.eridiumcorp.bagz.app.extensions.extractBoolean
import com.eridiumcorp.bagz.app.extensions.extractDouble
import com.eridiumcorp.bagz.app.extensions.extractMap
import com.eridiumcorp.bagz.app.extensions.extractMapList
import com.eridiumcorp.bagz.app.extensions.extractString

data class Transaction(
    val accountId: String,
    val amount: Int,
    val isoCurrencyCode: String? = null,
    val unofficialCurrencyCode: String? = null,
    val date: String,
    val location: Location,
    val name: String,
    val merchantName: String? = null,
    val originalDescription: String? = null,
    val paymentMeta: PaymentMeta,
    val pending: Boolean,
    val pendingTransactionId: String? = null,
    val accountOwner: String? = null,
    val transactionId: String,
    val logoUrl: String? = null,
    val website: String? = null,
    val authorizedDate: String? = null,
    val authorizedDatetime: String? = null,
    val datetime: String? = null,
    val paymentChannel: String,
    val personalFinanceCategory: PersonalFinanceCategory,
    val transactionCode: String? = null,
    val personalFinanceCategoryIconUrl: String? = null,
    val counterparties: List<Counterparty>,
    val merchantEntityId: String? = null,
    val account: Account,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Transaction {
            val transactionData = map.extractMap("transaction_data") ?: emptyMap()
            return Transaction(
                accountId = map.extractString("account_id") ?: "",
                amount = (transactionData.extractDouble("amount") ?: 0.0).toInt(),
                isoCurrencyCode = transactionData.extractString("iso_currency_code"),
                unofficialCurrencyCode = transactionData.extractString("unofficial_currency_code"),
                date = transactionData.extractString("date") ?: "",
                location = Location.fromMap(transactionData.extractMap("location") ?: emptyMap()),
                name = transactionData.extractString("name") ?: "",
                merchantName = transactionData.extractString("merchant_name"),
                originalDescription = transactionData.extractString("original_description"),
                paymentMeta = PaymentMeta.fromMap(
                    transactionData.extractMap("payment_meta") ?: emptyMap()
                ),
                pending = transactionData.extractBoolean("pending") == true,
                pendingTransactionId = transactionData.extractString("pending_transaction_id"),
                accountOwner = transactionData.extractString("account_owner"),
                transactionId = map.extractString("transaction_id") ?: "",
                logoUrl = transactionData.extractString("logo_url"),
                website = transactionData.extractString("website"),
                authorizedDate = transactionData.extractString("authorized_date"),
                authorizedDatetime = transactionData.extractString("authorized_datetime"),
                datetime = transactionData.extractString("datetime"),
                paymentChannel = transactionData.extractString("payment_channel") ?: "",
                personalFinanceCategory = PersonalFinanceCategory.fromMap(
                    transactionData.extractMap("personal_finance_category") ?: emptyMap()
                ),
                transactionCode = transactionData.extractString("transaction_code"),
                personalFinanceCategoryIconUrl = transactionData.extractString("personal_finance_category_icon_url"),
                counterparties = (transactionData.extractMapList("counterparties") ?: emptyList())
                    .map { Counterparty.fromMap(it) },
                merchantEntityId = transactionData.extractString("merchant_entity_id"),
                account = Account.fromMap(map.extractMap("account_data") ?: emptyMap())
            )
        }
    }
}
