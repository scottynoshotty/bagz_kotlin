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
            return Transaction(
                accountId = map.extractString("account_id") ?: "",
                amount = (map.extractDouble("amount") ?: 0.0).toInt(),
                isoCurrencyCode = map.extractString("iso_currency_code"),
                unofficialCurrencyCode = map.extractString("unofficial_currency_code"),
                date = map.extractString("date") ?: "",
                location = Location.fromMap(map.extractMap("location") ?: emptyMap()),
                name = map.extractString("name") ?: "",
                merchantName = map.extractString("merchant_name"),
                originalDescription = map.extractString("original_description"),
                paymentMeta = PaymentMeta.fromMap(map.extractMap("payment_meta") ?: emptyMap()),
                pending = map.extractBoolean("pending") == true,
                pendingTransactionId = map.extractString("pending_transaction_id"),
                accountOwner = map.extractString("account_owner"),
                transactionId = map.extractString("transaction_id") ?: "",
                logoUrl = map.extractString("logo_url"),
                website = map.extractString("website"),
                authorizedDate = map.extractString("authorized_date"),
                authorizedDatetime = map.extractString("authorized_datetime"),
                datetime = map.extractString("datetime"),
                paymentChannel = map.extractString("payment_channel") ?: "",
                personalFinanceCategory = PersonalFinanceCategory.fromMap(
                    map.extractMap("personal_finance_category") ?: emptyMap()
                ),
                transactionCode = map.extractString("transaction_code"),
                personalFinanceCategoryIconUrl = map.extractString("personal_finance_category_icon_url"),
                counterparties = (map.extractMapList("counterparties") ?: emptyList())
                    .map { Counterparty.fromMap(it) },
                merchantEntityId = map.extractString("merchant_entity_id"),
                account = Account.fromMap(map.extractMap("account_data") ?: emptyMap())
            )
        }
    }
}
