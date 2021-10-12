package com.example.newmovlex.ui.models

import java.io.Serializable


class User :Serializable{
    var id: Int = 0
    var name: String = ""
    var email: String = ""
    var email_verified_at: String = ""
    var created_at: String = ""
    var updated_at: String = ""
    var deleted_at: String = ""

    constructor() {

    }
}
