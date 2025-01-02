package com.eridiumcorp.bagz.app.models

import androidx.compose.ui.graphics.Color
import com.eridiumcorp.bagz.app.extensions.extractMap
import com.eridiumcorp.bagz.app.utils.currentMonthActivityKey
import kotlin.collections.mutableListOf

data class Activity(
    val activityData: List<ActivitySummary>?,
) {
    companion object {
        fun fromMap(map: Map<String, Any?>): Activity {
            val currentActivityKey = currentMonthActivityKey()
            val currentActivity = map.extractMap(currentActivityKey) ?: return Activity(null)
            val activitySummaryList =
                currentActivity.entries.fold(mutableListOf<ActivitySummary>()) { acc, entry ->
                    val primarySummary = PrimaryActivitySummary.fromEntry(entry)
                    val detailedSummary = DetailedActivitySummary.fromEntry(entry)
                    when {
                        primarySummary != null -> {
                            val existingIndex =
                                acc.indexOfFirst { it.primaryActivitySummary.type == primarySummary.type }
                            if (existingIndex != -1) {
                                val existingSummary = acc[existingIndex]
                                acc[existingIndex] =
                                    existingSummary.copy(primaryActivitySummary = primarySummary)
                            } else {
                                acc.add(ActivitySummary(primarySummary, emptyList()))
                            }
                        }

                        detailedSummary != null -> {
                            val existingIndex =
                                acc.indexOfFirst { it.primaryActivitySummary.type == detailedSummary.type.primary }
                            if (existingIndex != -1) {
                                val existingSummary = acc[existingIndex]
                                val updatedDetailedSummaries =
                                    existingSummary.detailedActivitySummaries.toMutableList()
                                        .apply { add(detailedSummary) }
                                acc[existingIndex] =
                                    existingSummary.copy(detailedActivitySummaries = updatedDetailedSummaries)
                            } else {
                                acc.add(
                                    ActivitySummary(
                                        PrimaryActivitySummary(
                                            detailedSummary.type.primary,
                                            detailedSummary.amount
                                        ), listOf(detailedSummary)
                                    )
                                )
                            }
                        }
                    }
                    acc
                }
            return Activity(activitySummaryList)
        }
    }
}

data class ActivitySummary(
    val primaryActivitySummary: PrimaryActivitySummary,
    val detailedActivitySummaries: List<DetailedActivitySummary>,
)

data class PrimaryActivitySummary(val type: PrimaryActivityType, val amount: Double) {

    companion object {
        fun fromEntry(entry: Map.Entry<String, Any?>): PrimaryActivitySummary? {
            val amount = when (entry.value) {
                is Double -> entry.value as Double
                is Int -> (entry.value as Int).toDouble()
                is Float -> (entry.value as Float).toDouble()
                else -> null
            }
            if (amount != null && amount >= 1) {
                val activityType = PrimaryActivityType.fromString(entry.key)
                if (activityType != null) {
                    return PrimaryActivitySummary(activityType, kotlin.math.abs(amount))
                }
            }
            return null
        }
    }
}

data class DetailedActivitySummary(val type: DetailedActivityType, val amount: Double) {

    companion object {
        fun fromEntry(entry: Map.Entry<String, Any?>): DetailedActivitySummary? {
            val amount = when (entry.value) {
                is Double -> entry.value as Double
                is Int -> (entry.value as Int).toDouble()
                is Float -> (entry.value as Float).toDouble()
                else -> null
            }
            if (amount != null && amount >= 1) {
                val activityType = DetailedActivityType.fromString(entry.key)
                if (activityType != null) {
                    return DetailedActivitySummary(activityType, kotlin.math.abs(amount))
                }
            }
            return null
        }
    }
}

enum class PrimaryActivityType(val color: Color) {
    INCOME(Color(0xFF00C853)),
    TRANSFER_IN(Color(0xFF00B0FF)),
    TRANSFER_OUT(Color(0xFFD50000)),
    LOAN_PAYMENTS(Color(0xFFFFD600)),
    BANK_FEES(Color(0xFFD50000)),
    ENTERTAINMENT(Color(0xFFAA00FF)),
    FOOD_AND_DRINK(Color(0xFF00BCD4)),
    GENERAL_MERCHANDISE(Color(0xFF64B5F6)),
    HOME_IMPROVEMENT(Color(0xFFFF9800)),
    MEDICAL(Color(0xFFE91E63)),
    PERSONAL_CARE(Color(0xFF9C27B0)),
    GENERAL_SERVICES(Color(0xFF009688)),
    GOVERNMENT_AND_NON_PROFIT(Color(0xFFF44336)),
    TRANSPORTATION(Color(0xFF2196F3)),
    TRAVEL(Color(0xFF4CAF50)),
    RENT_AND_UTILITIES(Color(0xFFFFC107));

    companion object {
        fun fromString(type: String): PrimaryActivityType? {
            return entries.find { it.name == type }
        }
    }
}

enum class DetailedActivityType(val color: Color, val primary: PrimaryActivityType) {
    // INCOME
    INCOME_DIVIDENDS(PrimaryActivityType.INCOME.color, PrimaryActivityType.INCOME),
    INCOME_INTEREST_EARNED(PrimaryActivityType.INCOME.color, PrimaryActivityType.INCOME),
    INCOME_RETIREMENT_PENSION(PrimaryActivityType.INCOME.color, PrimaryActivityType.INCOME),
    INCOME_TAX_REFUND(PrimaryActivityType.INCOME.color, PrimaryActivityType.INCOME),
    INCOME_UNEMPLOYMENT(PrimaryActivityType.INCOME.color, PrimaryActivityType.INCOME),
    INCOME_WAGES(PrimaryActivityType.INCOME.color, PrimaryActivityType.INCOME),
    INCOME_OTHER_INCOME(PrimaryActivityType.INCOME.color, PrimaryActivityType.INCOME),

    // TRANSFER_IN
    TRANSFER_IN_CASH_ADVANCES_AND_LOANS(
        PrimaryActivityType.TRANSFER_IN.color,
        PrimaryActivityType.TRANSFER_IN
    ),
    TRANSFER_IN_DEPOSIT(PrimaryActivityType.TRANSFER_IN.color, PrimaryActivityType.TRANSFER_IN),
    TRANSFER_IN_INVESTMENT_AND_RETIREMENT_FUNDS(
        PrimaryActivityType.TRANSFER_IN.color,
        PrimaryActivityType.TRANSFER_IN
    ),
    TRANSFER_IN_SAVINGS(PrimaryActivityType.TRANSFER_IN.color, PrimaryActivityType.TRANSFER_IN),
    TRANSFER_IN_ACCOUNT_TRANSFER(
        PrimaryActivityType.TRANSFER_IN.color,
        PrimaryActivityType.TRANSFER_IN
    ),
    TRANSFER_IN_OTHER_TRANSFER_IN(
        PrimaryActivityType.TRANSFER_IN.color,
        PrimaryActivityType.TRANSFER_IN
    ),

    // TRANSFER_OUT
    TRANSFER_OUT_INVESTMENT_AND_RETIREMENT_FUNDS(
        PrimaryActivityType.TRANSFER_OUT.color,
        PrimaryActivityType.TRANSFER_OUT
    ),
    TRANSFER_OUT_SAVINGS(PrimaryActivityType.TRANSFER_OUT.color, PrimaryActivityType.TRANSFER_OUT),
    TRANSFER_OUT_WITHDRAWAL(
        PrimaryActivityType.TRANSFER_OUT.color,
        PrimaryActivityType.TRANSFER_OUT
    ),
    TRANSFER_OUT_ACCOUNT_TRANSFER(
        PrimaryActivityType.TRANSFER_OUT.color,
        PrimaryActivityType.TRANSFER_OUT
    ),
    TRANSFER_OUT_OTHER_TRANSFER_OUT(
        PrimaryActivityType.TRANSFER_OUT.color,
        PrimaryActivityType.TRANSFER_OUT
    ),

    // LOAN_PAYMENTS
    LOAN_PAYMENTS_CAR_PAYMENT(
        PrimaryActivityType.LOAN_PAYMENTS.color,
        PrimaryActivityType.LOAN_PAYMENTS
    ),
    LOAN_PAYMENTS_CREDIT_CARD_PAYMENT(
        PrimaryActivityType.LOAN_PAYMENTS.color,
        PrimaryActivityType.LOAN_PAYMENTS
    ),
    LOAN_PAYMENTS_PERSONAL_LOAN_PAYMENT(
        PrimaryActivityType.LOAN_PAYMENTS.color,
        PrimaryActivityType.LOAN_PAYMENTS
    ),
    LOAN_PAYMENTS_MORTGAGE_PAYMENT(
        PrimaryActivityType.LOAN_PAYMENTS.color,
        PrimaryActivityType.LOAN_PAYMENTS
    ),
    LOAN_PAYMENTS_STUDENT_LOAN_PAYMENT(
        PrimaryActivityType.LOAN_PAYMENTS.color,
        PrimaryActivityType.LOAN_PAYMENTS
    ),
    LOAN_PAYMENTS_OTHER_PAYMENT(
        PrimaryActivityType.LOAN_PAYMENTS.color,
        PrimaryActivityType.LOAN_PAYMENTS
    ),

    // BANK_FEES
    BANK_FEES_ATM_FEES(PrimaryActivityType.BANK_FEES.color, PrimaryActivityType.BANK_FEES),
    BANK_FEES_FOREIGN_TRANSACTION_FEES(
        PrimaryActivityType.BANK_FEES.color,
        PrimaryActivityType.BANK_FEES
    ),
    BANK_FEES_INSUFFICIENT_FUNDS(
        PrimaryActivityType.BANK_FEES.color,
        PrimaryActivityType.BANK_FEES
    ),
    BANK_FEES_INTEREST_CHARGE(PrimaryActivityType.BANK_FEES.color, PrimaryActivityType.BANK_FEES),
    BANK_FEES_OVERDRAFT_FEES(PrimaryActivityType.BANK_FEES.color, PrimaryActivityType.BANK_FEES),
    BANK_FEES_OTHER_BANK_FEES(PrimaryActivityType.BANK_FEES.color, PrimaryActivityType.BANK_FEES),

    // ENTERTAINMENT
    ENTERTAINMENT_CASINOS_AND_GAMBLING(
        PrimaryActivityType.ENTERTAINMENT.color,
        PrimaryActivityType.ENTERTAINMENT
    ),
    ENTERTAINMENT_MUSIC_AND_AUDIO(
        PrimaryActivityType.ENTERTAINMENT.color,
        PrimaryActivityType.ENTERTAINMENT
    ),
    ENTERTAINMENT_SPORTING_EVENTS_AMUSEMENT_PARKS_AND_MUSEUMS(
        PrimaryActivityType.ENTERTAINMENT.color,
        PrimaryActivityType.ENTERTAINMENT
    ),
    ENTERTAINMENT_TV_AND_MOVIES(
        PrimaryActivityType.ENTERTAINMENT.color,
        PrimaryActivityType.ENTERTAINMENT
    ),
    ENTERTAINMENT_VIDEO_GAMES(
        PrimaryActivityType.ENTERTAINMENT.color,
        PrimaryActivityType.ENTERTAINMENT
    ),
    ENTERTAINMENT_OTHER_ENTERTAINMENT(
        PrimaryActivityType.ENTERTAINMENT.color,
        PrimaryActivityType.ENTERTAINMENT
    ),

    // FOOD_AND_DRINK
    FOOD_AND_DRINK_BEER_WINE_AND_LIQUOR(
        PrimaryActivityType.FOOD_AND_DRINK.color,
        PrimaryActivityType.FOOD_AND_DRINK
    ),
    FOOD_AND_DRINK_COFFEE(
        PrimaryActivityType.FOOD_AND_DRINK.color,
        PrimaryActivityType.FOOD_AND_DRINK
    ),
    FOOD_AND_DRINK_FAST_FOOD(
        PrimaryActivityType.FOOD_AND_DRINK.color,
        PrimaryActivityType.FOOD_AND_DRINK
    ),
    FOOD_AND_DRINK_GROCERIES(
        PrimaryActivityType.FOOD_AND_DRINK.color,
        PrimaryActivityType.FOOD_AND_DRINK
    ),
    FOOD_AND_DRINK_RESTAURANT(
        PrimaryActivityType.FOOD_AND_DRINK.color,
        PrimaryActivityType.FOOD_AND_DRINK
    ),
    FOOD_AND_DRINK_VENDING_MACHINES(
        PrimaryActivityType.FOOD_AND_DRINK.color,
        PrimaryActivityType.FOOD_AND_DRINK
    ),
    FOOD_AND_DRINK_OTHER_FOOD_AND_DRINK(
        PrimaryActivityType.FOOD_AND_DRINK.color,
        PrimaryActivityType.FOOD_AND_DRINK
    ),

    // GENERAL_MERCHANDISE
    GENERAL_MERCHANDISE_BOOKSTORES_AND_NEWSSTANDS(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_CLOTHING_AND_ACCESSORIES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_CONVENIENCE_STORES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_DEPARTMENT_STORES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_DISCOUNT_STORES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_ELECTRONICS(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_GIFTS_AND_NOVELTIES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_OFFICE_SUPPLIES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_ONLINE_MARKETPLACES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_PET_SUPPLIES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_SPORTING_GOODS(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_SUPERSTORES(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_TOBACCO_AND_VAPE(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),
    GENERAL_MERCHANDISE_OTHER_GENERAL_MERCHANDISE(
        PrimaryActivityType.GENERAL_MERCHANDISE.color,
        PrimaryActivityType.GENERAL_MERCHANDISE
    ),

    // HOME_IMPROVEMENT
    HOME_IMPROVEMENT_FURNITURE(
        PrimaryActivityType.HOME_IMPROVEMENT.color,
        PrimaryActivityType.HOME_IMPROVEMENT
    ),
    HOME_IMPROVEMENT_HARDWARE(
        PrimaryActivityType.HOME_IMPROVEMENT.color,
        PrimaryActivityType.HOME_IMPROVEMENT
    ),
    HOME_IMPROVEMENT_REPAIR_AND_MAINTENANCE(
        PrimaryActivityType.HOME_IMPROVEMENT.color,
        PrimaryActivityType.HOME_IMPROVEMENT
    ),
    HOME_IMPROVEMENT_SECURITY(
        PrimaryActivityType.HOME_IMPROVEMENT.color,
        PrimaryActivityType.HOME_IMPROVEMENT
    ),
    HOME_IMPROVEMENT_OTHER_HOME_IMPROVEMENT(
        PrimaryActivityType.HOME_IMPROVEMENT.color,
        PrimaryActivityType.HOME_IMPROVEMENT
    ),

    // MEDICAL
    MEDICAL_DENTAL_CARE(PrimaryActivityType.MEDICAL.color, PrimaryActivityType.MEDICAL),
    MEDICAL_EYE_CARE(PrimaryActivityType.MEDICAL.color, PrimaryActivityType.MEDICAL),
    MEDICAL_NURSING_CARE(PrimaryActivityType.MEDICAL.color, PrimaryActivityType.MEDICAL),
    MEDICAL_PHARMACIES_AND_SUPPLEMENTS(
        PrimaryActivityType.MEDICAL.color,
        PrimaryActivityType.MEDICAL
    ),
    MEDICAL_PRIMARY_CARE(PrimaryActivityType.MEDICAL.color, PrimaryActivityType.MEDICAL),
    MEDICAL_VETERINARY_SERVICES(PrimaryActivityType.MEDICAL.color, PrimaryActivityType.MEDICAL),
    MEDICAL_OTHER_MEDICAL(PrimaryActivityType.MEDICAL.color, PrimaryActivityType.MEDICAL),

    // PERSONAL_CARE
    PERSONAL_CARE_GYMS_AND_FITNESS_CENTERS(
        PrimaryActivityType.PERSONAL_CARE.color,
        PrimaryActivityType.PERSONAL_CARE
    ),
    PERSONAL_CARE_HAIR_AND_BEAUTY(
        PrimaryActivityType.PERSONAL_CARE.color,
        PrimaryActivityType.PERSONAL_CARE
    ),
    PERSONAL_CARE_LAUNDRY_AND_DRY_CLEANING(
        PrimaryActivityType.PERSONAL_CARE.color,
        PrimaryActivityType.PERSONAL_CARE
    ),
    PERSONAL_CARE_OTHER_PERSONAL_CARE(
        PrimaryActivityType.PERSONAL_CARE.color,
        PrimaryActivityType.PERSONAL_CARE
    ),

    // GENERAL_SERVICES
    GENERAL_SERVICES_ACCOUNTING_AND_FINANCIAL_PLANNING(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_AUTOMOTIVE(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_CHILDCARE(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_CONSULTING_AND_LEGAL(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_EDUCATION(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_INSURANCE(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_POSTAGE_AND_SHIPPING(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_STORAGE(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),
    GENERAL_SERVICES_OTHER_GENERAL_SERVICES(
        PrimaryActivityType.GENERAL_SERVICES.color,
        PrimaryActivityType.GENERAL_SERVICES
    ),

    // GOVERNMENT_AND_NON_PROFIT
    GOVERNMENT_AND_NON_PROFIT_DONATIONS(
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT.color,
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT
    ),
    GOVERNMENT_AND_NON_PROFIT_GOVERNMENT_DEPARTMENTS_AND_AGENCIES(
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT.color,
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT
    ),
    GOVERNMENT_AND_NON_PROFIT_TAX_PAYMENT(
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT.color,
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT
    ),
    GOVERNMENT_AND_NON_PROFIT_OTHER_GOVERNMENT_AND_NON_PROFIT(
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT.color,
        PrimaryActivityType.GOVERNMENT_AND_NON_PROFIT
    ),

    // TRANSPORTATION
    TRANSPORTATION_BIKES_AND_SCOOTERS(
        PrimaryActivityType.TRANSPORTATION.color,
        PrimaryActivityType.TRANSPORTATION
    ),
    TRANSPORTATION_GAS(
        PrimaryActivityType.TRANSPORTATION.color,
        PrimaryActivityType.TRANSPORTATION
    ),
    TRANSPORTATION_PARKING(
        PrimaryActivityType.TRANSPORTATION.color,
        PrimaryActivityType.TRANSPORTATION
    ),
    TRANSPORTATION_PUBLIC_TRANSIT(
        PrimaryActivityType.TRANSPORTATION.color,
        PrimaryActivityType.TRANSPORTATION
    ),
    TRANSPORTATION_TAXIS_AND_RIDE_SHARES(
        PrimaryActivityType.TRANSPORTATION.color,
        PrimaryActivityType.TRANSPORTATION
    ),
    TRANSPORTATION_TOLLS(
        PrimaryActivityType.TRANSPORTATION.color,
        PrimaryActivityType.TRANSPORTATION
    ),
    TRANSPORTATION_OTHER_TRANSPORTATION(
        PrimaryActivityType.TRANSPORTATION.color,
        PrimaryActivityType.TRANSPORTATION
    ),

    // TRAVEL
    TRAVEL_FLIGHTS(PrimaryActivityType.TRAVEL.color, PrimaryActivityType.TRAVEL),
    TRAVEL_LODGING(PrimaryActivityType.TRAVEL.color, PrimaryActivityType.TRAVEL),
    TRAVEL_RENTAL_CARS(PrimaryActivityType.TRAVEL.color, PrimaryActivityType.TRAVEL),
    TRAVEL_OTHER_TRAVEL(PrimaryActivityType.TRAVEL.color, PrimaryActivityType.TRAVEL),

    // RENT_AND_UTILITIES
    RENT_AND_UTILITIES_GAS_AND_ELECTRICITY(
        PrimaryActivityType.RENT_AND_UTILITIES.color,
        PrimaryActivityType.RENT_AND_UTILITIES
    ),
    RENT_AND_UTILITIES_INTERNET_AND_CABLE(
        PrimaryActivityType.RENT_AND_UTILITIES.color,
        PrimaryActivityType.RENT_AND_UTILITIES
    ),
    RENT_AND_UTILITIES_RENT(
        PrimaryActivityType.RENT_AND_UTILITIES.color,
        PrimaryActivityType.RENT_AND_UTILITIES
    ),
    RENT_AND_UTILITIES_SEWAGE_AND_WASTE_MANAGEMENT(
        PrimaryActivityType.RENT_AND_UTILITIES.color,
        PrimaryActivityType.RENT_AND_UTILITIES
    ),
    RENT_AND_UTILITIES_TELEPHONE(
        PrimaryActivityType.RENT_AND_UTILITIES.color,
        PrimaryActivityType.RENT_AND_UTILITIES
    ),
    RENT_AND_UTILITIES_WATER(
        PrimaryActivityType.RENT_AND_UTILITIES.color,
        PrimaryActivityType.RENT_AND_UTILITIES
    ),
    RENT_AND_UTILITIES_OTHER_UTILITIES(
        PrimaryActivityType.RENT_AND_UTILITIES.color,
        PrimaryActivityType.RENT_AND_UTILITIES
    );

    companion object {
        fun fromString(type: String): DetailedActivityType? {
            return entries.find { it.name == type }
        }
    }
}
