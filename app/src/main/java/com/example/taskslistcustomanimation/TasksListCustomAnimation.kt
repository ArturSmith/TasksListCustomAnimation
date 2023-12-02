package com.example.taskslistcustomanimation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksListCustomAnimation(tasks: List<String>) {

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("List animation") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) {
        Box(
            Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn {
                items(items = tasks) {
                    val index = when {
                        tasks.size == 1 -> 0
                        it == tasks.first() -> 1
                        it == tasks.last() -> 2
                        else -> 3
                    }
                    TaskCard(index, it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskCard(elementPosition: Int, task: String) {
    val defaultHeight = TextFieldDefaults.MinHeight
    val expanded = rememberSaveable { mutableStateOf(false) }
    val transition = updateTransition(expanded, label = "ContentSizeTransition")
    val contentHeight = transition.animateDp(
        transitionSpec = {
            keyframes {
                durationMillis = 300
            }
        }, label = ""
    ) { expandedState ->
        if (expandedState.value) 200.dp else defaultHeight
    }
    Row(
        Modifier
            .drawBehind {
                drawLine(
                    color = if (elementPosition == 0) Color.Transparent else Color.Black,
                    strokeWidth = 1.dp.toPx(),
                    start = Offset(0f, if (elementPosition == 1) size.height / 2 else 0f),
                    end = Offset(0f, if (elementPosition == 2) size.height / 2 else size.height)
                )
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .background(color = Color.Black)
                .height(1.dp)
                .width(15.dp)
        )
        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (expanded.value) Color.Green else Color.Black,
                    shape = CircleShape
                ).size(15.dp)
        )
        Box(
            Modifier.padding(10.dp)
                .width(TextFieldDefaults.MinWidth)
                .animateContentSize()
                .height(contentHeight.value)
                .border(
                    width = 1.dp,
                    color = if (expanded.value) Color.Green else Color.Black,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable {
                    expanded.value = !expanded.value
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                task,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}