package com.ahriman.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    var number:Int = 20
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        val actionButton = findViewById<FloatingActionButton>(R.id.addItemButton)

        recyclerview.layoutManager = LinearLayoutManager(this)

        val data = ArrayList<ItemViewModel>()

        for (i in 1..20) {
            data.add(ItemViewModel(com.google.android.material.R.drawable.ic_clock_black_24dp, "Item " + i))
        }

        val adapter by lazy { ViewAdapter() }
        adapter.differ.submitList(data)

        recyclerview.adapter = adapter
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val deletedCourse: ItemViewModel =
                    data[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition

                data.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(recyclerview, "Deleted " + deletedCourse.text, Snackbar.LENGTH_LONG)
                    .setAction(
                        "Undo"
                    ) {

                        data.add(position, deletedCourse)

                        adapter.notifyItemInserted(position)
                    }.show()
            }

        }).attachToRecyclerView(recyclerview)

        actionButton.setOnClickListener {
            number++
            data.add(ItemViewModel(com.google.android.material.R.drawable.ic_clock_black_24dp, "Item " + number))
            adapter.notifyItemInserted(data.size - 1)


        }
    }
}