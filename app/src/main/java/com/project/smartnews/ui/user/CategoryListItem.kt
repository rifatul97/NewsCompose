package com.project.smartnews.ui.user

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CategoryListItem(
    categoryName: String,
    check: () -> Boolean,
    onChecked: () -> Unit,
    loadCategories: () -> Unit
){ //, update: () -> Unit) {

    val checkedState = remember { mutableStateOf(check.invoke())}

    Row(modifier = Modifier.height(50.dp)) {
        Text(text = categoryName,
            modifier = Modifier.weight(0.75f))//.padding(16.dp))

        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                val newCheck = onChecked.invoke()
                checkedState.value = !checkedState.value
                loadCategories.invoke()
            },
            modifier = Modifier.weight(0.25f)
        )
    }

}