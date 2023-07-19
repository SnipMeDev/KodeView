package dev.snipme.androidexample

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun Dropdown(
    options: List<String>,
    selected: Int,
    modifier: Modifier = Modifier,
    onSelect: (String) -> Unit,
) {
    var selectedOption by remember {
        mutableStateOf(options[selected])
    }

    var isExpanded by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {
        DropdownMenu(
            modifier = Modifier.background(Color.White),
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier,
                    text = { Text(text = option) },
                    onClick = {
                        selectedOption = option
                        onSelect(option)
                        isExpanded = !isExpanded
                    },
                )
            }
        }

        Text(
            text = selectedOption,
            textAlign = TextAlign.Center,
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
                .clickable { isExpanded = !isExpanded }
                .padding(8.dp),
        )
    }
}