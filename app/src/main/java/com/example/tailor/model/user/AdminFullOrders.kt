package com.example.tailor.model.user

data class AdminFullOrders(
    var customerDetails: UserModel,
    var customerSize: BodyMeasurement,
    var customerOrders: Orders
)
