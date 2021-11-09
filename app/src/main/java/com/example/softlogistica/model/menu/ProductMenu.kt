package com.example.softlogistica.model.menu

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product_menu")
    data class ProductMenu constructor(
        @PrimaryKey(autoGenerate = true)
        val id: Long,

        @ColumnInfo(name="name")
        val name: String?,

        @ColumnInfo(name="iamge")
        val image: Int?,

    ) : Parcelable
