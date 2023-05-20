package com.example.roomdemo

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assitants")

class Assitant {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "assistanId")
    var id: Int=0

    @ColumnInfo(name = "assitantName")
    var assitantName: String = ""
    var quantityPay: Int = 0


    @ColumnInfo(name = "inscriptionDate")
    var inscriptionDate: String = ""

    @ColumnInfo(name = "bloodType")
    var bloodType: String = ""

    @ColumnInfo(name = "phone")
    var phone: String = ""

    @ColumnInfo(name = "email")
    var email: String = ""

    constructor()

    constructor(assitantName: String, quantityPay: Int, inscriptionDate: String, bloodType: String, phone: String, email: String){
        this.assitantName = assitantName
        this.quantityPay = quantityPay
        this.inscriptionDate = inscriptionDate
        this.bloodType = bloodType
        this.phone = phone
        this.email = email
    }

    constructor(assitantName: String, quantityPay: Int){
        this.assitantName = assitantName
        this.quantityPay = quantityPay
    }

}