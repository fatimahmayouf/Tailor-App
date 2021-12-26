package com.example.tailor.model.user

data class Orders(
    var orderDate: String? = null,
    var orderImg: String? = null,
    var orderPrice: Double? = null,
    var orderNotes: String? = null,
    var orderApproval: Boolean? = null

)
