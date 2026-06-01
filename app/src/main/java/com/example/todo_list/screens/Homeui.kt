package com.example.todo_list.screens

import android.R.attr.padding
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo_list.data.Taskitem
import com.example.todo_list.viewmodel.viewmodel
import kotlin.getValue

@Composable
fun Homeui(viewmodel: viewmodel) {

    var showSheet by rememberSaveable { mutableStateOf(false) }
    var isEdit by rememberSaveable { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Taskitem?>(null) }

    val tasks = viewmodel.viewalltasks.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isEdit = false
                    selectedTask = null
                    showSheet = true
                }
            ) {
                Icon(Icons.Default.Add, null)
            }
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            if (tasks.value.isEmpty()) {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No Tasks Available")
                }

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp)
                ) {

                    items(tasks.value) { task ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {

                            TaskCard(
                                task = task,

                                onToggleDone = {

                                    viewmodel.updateTask(
                                        task.copy(
                                            isDone = !task.isDone
                                        )
                                    )

                                },

                                onEditClick = {

                                    selectedTask = task
                                    isEdit = true
                                    showSheet = true

                                },

                                onDeleteClick = {

                                    viewmodel.deleteTask(task)

                                }
                            )
                        }
                    }
                }
            }

            if (showSheet) {

                CreateTask(
                    viewmodel = viewmodel,
                    isEdit = isEdit,
                    task = selectedTask,
                    onDismiss = {
                        showSheet = false
                    }
                )
            }
        }
    }
}
@Composable
fun TaskCard(
    task: Taskitem,
    onToggleDone: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onToggleDone
        ) {
            Icon(
                imageVector =
                    if (task.isDone)
                        Icons.Default.CheckCircle
                    else
                        Icons.Default.RadioButtonUnchecked,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = task.name,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                textDecoration =
                    if (task.isDone)
                        TextDecoration.LineThrough
                    else
                        TextDecoration.None
            )
        )

        IconButton(
            onClick = onEditClick
        ) {
            Icon(
                Icons.Default.Edit,
                contentDescription = null
            )
        }

        IconButton(
            onClick = onDeleteClick
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = null
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTask(
    viewmodel: viewmodel,
    isEdit: Boolean,
    task: Taskitem?,
    onDismiss: () -> Unit
) {

    var taskName by remember {
        mutableStateOf(task?.name ?: "")
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                text = if (isEdit) "Edit Task" else "Create Task",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = taskName,
                onValueChange = {
                    taskName = it
                },
                label = {
                    Text("Task Name")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {

                    if (taskName.isNotBlank()) {

                        if (isEdit && task != null) {

                            viewmodel.updateTask(
                                task.copy(
                                    name = taskName,
                                    isDone = false
                                )
                            )

                        } else {

                            viewmodel.addTask(
                                Taskitem(
                                    name = taskName,
                                    isDone = false
                                )
                            )
                        }

                        onDismiss()
                    }
                }
            ) {
                Text(
                    if (isEdit)
                        "Update Task"
                    else
                        "Create Task"
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
