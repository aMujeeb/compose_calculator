package com.mujeeapps.jetpacktest.utils

fun calculateTotalTip(totalBill: String, tipPercentage: Int): Double {
    return if (totalBill.isNotEmpty() && totalBill.toDouble() > 1)
        (totalBill.toDouble() * tipPercentage) / 100 else 0.0
}

fun calculateTotalPerPerson(totalBill: String, splitBy: Int, tipPercentage: Int): Double {
    val bill = calculateTotalTip(totalBill, tipPercentage) + totalBill.toDouble()
    return bill / splitBy
}