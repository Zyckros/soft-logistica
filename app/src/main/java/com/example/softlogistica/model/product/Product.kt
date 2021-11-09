
package com.example.softlogistica.model.product

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product", indices = [Index(value = ["id"])])
data class Product constructor(
    @PrimaryKey( autoGenerate = true)
    var id: Long?,

    var internal_code :Int?,

    var description: String?,

    var broad_description: String?,

    var buy_date: String?,

    var registration_date : String?,

    var name : String?,

    var brand : String?,

    var model: String?,

    var license: String?,

    var serial_number : String?,

    var buy_price : Double?,

    var loadmax_m3 : Double?,

    var loadmax_tons : Double?,

    var itv : String?,

    var insurante : String?,

    var high: Int?,

    var width: Int?,

    var depth: Int?,

    var weight : Int?,

    var fuel : String?,

    var number_chassis: String?,

    var frequenzy_hz : Double?,

    var intensity_amp : Double?,

    var length_mm : Int?,

    var license_trailer : String?,

    var negociated_price : Double?,

    var tare : Int?,

    var number_axle : String?,

    var power : Int?,

    var par : Int?,

    var voltage : Int?,

    var rentalprice_day : Double?,

    var rentalprice_hour : Double?,

    var rentalprice_km : Double?,

    var rentalprice_month : Double?,

    var journey_price : Double?,

    var sale_price : Double?,

    var photo_product : String?,

    var technicalsheet : String?,

    var key_words : String?,

    var type : String?,

    var manufactureDate : String?,

    var supplier : String?,

    var familyID : Int?,

    var available : Boolean?,

    ) : Parcelable
