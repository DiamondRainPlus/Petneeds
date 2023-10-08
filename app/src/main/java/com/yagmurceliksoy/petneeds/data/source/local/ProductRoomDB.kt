package com.yagmurceliksoy.petneeds.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yagmurceliksoy.petneeds.data.model.ProductEntity


@Database(entities = [ProductEntity::class], version = 1)
abstract class ProductRoomDB : RoomDatabase(){

    abstract fun productsDao(): ProductDao
}