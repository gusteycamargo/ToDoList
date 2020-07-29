package com.camargo.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.camargo.todolist.adapters.ToDoAdapter
import com.camargo.todolist.adapters.ToDoListener
import com.camargo.todolist.model.ToDo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ToDoListener {

    private lateinit var adapter: ToDoAdapter
    private var indexDaAtividadeSelecionada = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        add_bt.setOnClickListener {
            insertItem()
        }

        remove_bt.setOnClickListener {
            removeItem()
        }

        adapter = ToDoAdapter(this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)

        reset()
    }

    fun insertItem() {
//        val index = adapter.
        var titulo = title_text.text.toString()
        var descricao = description_text.text.toString()

        val newItem = ToDo(
            "[NÃO FEITO]",
            titulo,
            descricao
        )

        val position = adapter.add(newItem)
        (recycler_view.layoutManager as LinearLayoutManager).scrollToPosition(position)
        reset()
    }

    fun updateItem() {
        val clickedItem = adapter.getToDoInPosition(indexDaAtividadeSelecionada)
        clickedItem.title = title_text.text.toString()
        clickedItem.description = description_text.text.toString()

        adapter.notifyItemChanged(indexDaAtividadeSelecionada)

        reset()
    }

    fun removeItem() {
        adapter.removeToDoInPosition(indexDaAtividadeSelecionada)
        reset()
    }

    fun reset() {
        title_text.text = Editable.Factory.getInstance().newEditable("")
        description_text.text = Editable.Factory.getInstance().newEditable("")
        add_bt.text = "Adicionar"
        add_bt.setOnClickListener {
            insertItem()
        }
        remove_bt.isClickable=false
        remove_bt.visibility= View.INVISIBLE
    }

    override fun onItemClick(todo: ToDo) {
        indexDaAtividadeSelecionada = adapter.returnPositionOfToDo(todo)
        val clickedItem = adapter.getToDoInPosition(indexDaAtividadeSelecionada)
        title_text.text = Editable.Factory.getInstance().newEditable(clickedItem.title)
        description_text.text = Editable.Factory.getInstance().newEditable(clickedItem.description)

        add_bt.text = "Editar"
        add_bt.setOnClickListener {
            updateItem()
        }
        remove_bt.isClickable=true
        remove_bt.visibility= View.VISIBLE // v letter should be capital
    }

    override fun onLongClick(todo: ToDo) {
        indexDaAtividadeSelecionada = adapter.returnPositionOfToDo(todo)
        val clickedItem = adapter.getToDoInPosition(indexDaAtividadeSelecionada)

        if(clickedItem.status == "[NÃO FEITO]") {
            clickedItem.status = "[FEITO]"
        }
        else if(clickedItem.status == "[FEITO]") {
            clickedItem.status = "[NÃO FEITO]"
        }

        adapter.notifyItemChanged(indexDaAtividadeSelecionada)
    }

//    private fun generateDummyList(size: Int): ArrayList<Item> {
//        val list = ArrayList<Item>()
//        for (i in 0 until size) {
//            val drawable = when (i % 3) {
//                0 -> R.drawable.ic_android
//                1 -> R.drawable.ic_android
//                else -> R.drawable.ic_android
//            }
//            val item = Item(drawable, "Item $i", "Line 2")
//            list += item
//        }
//        return list
//    }
}