package com.typer.typerush.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.typer.typerush.ui.theme.TypeRushColors
import kotlin.let

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    label: String? = null,
    trailingIcon: ImageVector? = null,
    onClick: () -> Unit = {},
    placeholder: String? = null,
    minLines: Int = 1,
    maxLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { label?.let { Text(it) }},
        trailingIcon = {
            trailingIcon?.let {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = null,
                        tint = TypeRushColors.Primary
                    )
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = TypeRushColors.SurfaceGlass,
            unfocusedContainerColor = TypeRushColors.SurfaceGlass,
            focusedIndicatorColor = TypeRushColors.Primary,
            unfocusedIndicatorColor = TypeRushColors.BorderGlass,
            focusedLabelColor = TypeRushColors.Primary,
            unfocusedLabelColor = TypeRushColors.TextSecondary,
            cursorColor = TypeRushColors.Primary,
            focusedTextColor = TypeRushColors.TextPrimary,
            unfocusedTextColor = TypeRushColors.TextSecondary,
            focusedLeadingIconColor = TypeRushColors.Primary,
            unfocusedLeadingIconColor = TypeRushColors.TextSecondary
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth(),
        placeholder = {
            placeholder?.let {
                Text(
                    text = it,
                    color = TypeRushColors.TextSecondary.copy(alpha = 0.6f)
                )
            }
        },
        minLines = minLines,
        maxLines = maxLines
    )
}