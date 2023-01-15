package com.ahriman.recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemViewModel: ViewModel() {
    var itemList: MutableLiveData<List<Item>> = MutableLiveData()
    init {
        itemList.value = ItemData.getUsers()
    }
    fun refreshList(items: ArrayList<Item>){
        itemList.value = items
    }
    fun GetListUser() = itemList
    //fun DeleteItem(position: Int) = itemList.remove

    /*fun updateListItem(){
        itemList.value =
    }*/


}