package com.ubayadev.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ubayadev.todoapp.model.Todo
import com.ubayadev.todoapp.model.TodoDatabase
import com.ubayadev.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application):AndroidViewModel(application),
    CoroutineScope {
    private var job = Job()
    val todoLD = MutableLiveData<Todo>()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun fetch(uuid:Int) {
        launch {
            todoLD.postValue(buildDb(getApplication()).todoDao().selectTodo(uuid))
        }
    }

    fun addTodo(todo:Todo) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().insertAll(todo)
        }
    }
}