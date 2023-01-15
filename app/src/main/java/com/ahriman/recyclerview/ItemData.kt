package com.ahriman.recyclerview

object ItemData {
    fun getUsers(): ArrayList<Item>{
        val data = ArrayList<Item>()
        for (i in 1..20) {
            data.add(
                Item(
                    com.google.android.material.R.drawable.ic_clock_black_24dp,
                    "Item " + i
                )
            )
        }
        return data
    }
}