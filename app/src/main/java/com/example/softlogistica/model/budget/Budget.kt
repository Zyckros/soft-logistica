package com.example.softlogistica.model.budget

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "budget")
data class Budget(

    @PrimaryKey(autoGenerate = true)
    val id : Long?,

    @ColumnInfo(name = "budget_code")
    val budgetCode : String?,

    @ColumnInfo(name = "date")
    val date : String?,

    @ColumnInfo(name = "price")
    val Totalprice : Double?,

    @ColumnInfo(name = "customer_id")
    val customerId : Long?,

    @ColumnInfo(name = "employee_id")
    val employeeId : Long?,

    @ColumnInfo(name = "budget_product_list")
    val budget_product_list : Int?,

    @ColumnInfo(name = "budget_status")
    val budgetStatus : String?,

    ) : Parcelable
