package com.example.tailor.model.user

data class Orders(
    var orderDate: String,
    var orderImg: String,
    var orderPrice: String,
    var orderApproval: Boolean = false,
    var orderNotes: String

)
