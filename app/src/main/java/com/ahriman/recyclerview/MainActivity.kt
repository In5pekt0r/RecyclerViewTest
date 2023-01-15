package com.ahriman.recyclerview

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {


    var number:Int = 20

    val ARGS_SCROLL_STATE:String = "key_list"
    lateinit var recyclerview: RecyclerView

    lateinit var data: ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview = findViewById(com.ahriman.recyclerview.R.id.recyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val actionButton = findViewById<FloatingActionButton>(R.id.addItemButton)

        if(savedInstanceState != null){
            onRestoreInstanceState(savedInstanceState)
        }
        else data = ItemData.getUsers()




        val adapter by lazy { ViewAdapter() }


        recyclerview.adapter = adapter
        adapter.differ.submitList(data)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val deletedCourse: Item =
                    data[viewHolder.adapterPosition]

                val position = viewHolder.adapterPosition

                data.removeAt(viewHolder.adapterPosition)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                //onSaveInstanceState(savedInstanceState)

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
            data.add(
                Item(
                    com.google.android.material.R.drawable.ic_clock_black_24dp,
                    "Item " + number
                )
            )
            adapter.notifyItemInserted(data.size - 1)


        }

    }

    /*override fun onSaveInstanceState(outState: Bundle) {
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        super.onSaveInstanceState(outState)

        outState.putParcelable(
            ARGS_SCROLL_STATE,
            recyclerview.getLayoutManager()?.onSaveInstanceState()
        )
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i("MyTag", "onRestoreInstanceState")
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerview.layoutManager = savedInstanceState.getParcelable(ARGS_SCROLL_STATE)
    }*/
    override fun onSaveInstanceState(state: Bundle) {



        state.putParcelableArrayList(ARGS_SCROLL_STATE, data as ArrayList<out Parcelable?>?)
        super.onSaveInstanceState(state)
    }
    override fun onRestoreInstanceState(state: Bundle) {
        super.onRestoreInstanceState(state)

        if (state != null) data = state.getParcelableArrayList<Parcelable>(ARGS_SCROLL_STATE) as ArrayList<Item>
    }



    }



