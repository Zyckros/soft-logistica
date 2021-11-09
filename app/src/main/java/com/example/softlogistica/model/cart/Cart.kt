package com.example.softlogistica.model.cart

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "cart")

/*
*  foreignKeys = [
    ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["product_id"],
        onDelete = ForeignKey.CASCADE
    )]*/
data class Cart constructor(

    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "product_id")
    var product_id : Long?,

    @ColumnInfo(name = "init_rental_date")
    var init_rental_date : String?,

    @ColumnInfo(name = "end_rental_date")
    var end_rental_date : String?,

    @ColumnInfo(name = "buy_or_rental")
    var buy_or_rental : String?,

    ) : Parcelable