package dev.snipme.androidexample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.FormatAlignLeft
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.FormatAlignLeft
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Reorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LineNumberSwitcher(
    lineNumbersEnabled: Boolean,
    modifier: Modifier = Modifier,
    onChange: (Boolean) -> Unit,
) {
    Surface {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(Modifier.width(8.dp))
            Icon(Icons.Outlined.FormatListNumbered, contentDescription = "Line numbers enabled")
            Spacer(Modifier.width(16.dp))
            Switch(checked = lineNumbersEnabled, onCheckedChange = onChange)
            Spacer(Modifier.width(16.dp))
            Icon(Icons.Outlined.Reorder, contentDescription = "Dark mode")
            Spacer(Modifier.width(8.dp))
        }
    }
}