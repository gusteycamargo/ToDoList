package com.camargo.todolist.adapters

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.camargo.todolist.R
import com.camargo.todolist.model.ToDo
import kotlinx.android.synthetic.main.item.view.*

class ToDoAdapter(
    private val listener: ToDoListener
) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
    private val toDoList: MutableList<ToDo> = mutableListOf<ToDo>()

    fun add(todo: ToDo): Int {
        val position = 0
        toDoList.add(position, todo)
        notifyItemInserted(position)
        return position
    }

    fun getToDoInPosition(position: Int): ToDo {
        return toDoList[position]
    }

    fun removeToDoInPosition(position: Int) {
        toDoList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun returnPositionOfToDo(todo: ToDo): Int {
        return toDoList.indexOf(todo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,
            parent, false)
        return ViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = toDoList[position]
        holder.fillView(todo)
    }
    override fun getItemCount() = toDoList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun fillView(todo: ToDo) {
            itemView.textView1.text = Editable.Factory.getInstance().newEditable(todo.status + " " + todo.title)
            itemView.text_view_2.text = Editable.Factory.getInstance().newEditable(todo.description)

            itemView.setOnClickListener {
                listener.onItemClick(todo)
            }

            itemView.setOnLongClickListener {
                listener.onLongClick(todo)
                true
            }
        }
    }
}

