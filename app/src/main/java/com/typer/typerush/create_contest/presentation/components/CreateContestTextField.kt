package com.typer.typerush.create_contest.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.typer.typerush.core.components.CustomTextField
import com.typer.typerush.ui.theme.TypeRushColors

@Composable
fun CreateContestTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    minLines: Int = 1,
    maxLines: Int = Int.MAX_VALUE,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
                tint = TypeRushColors.Primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                ),
                color = TypeRushColors.TextPrimary
            )
        }
        CustomTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            minLines = minLines,
            maxLines = maxLines,
        )

//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            placeholder = {
//                Text(
//                    text = placeholder,
//                    color = TypeRushColors.TextSecondary.copy(alpha = 0.6f)
//                )
//            },
//            modifier = Modifier.fillMaxWidth(),
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = TypeRushColors.Primary,
//                unfocusedBorderColor = Color.Transparent,
//                focusedContainerColor = Color.White.copy(alpha = 0.05f),
//                unfocusedContainerColor = Color.White.copy(alpha = 0.02f),
//                focusedTextColor = TypeRushColors.TextPrimary,
//                unfocusedTextColor = TypeRushColors.TextPrimary
//            ),
//            shape = RoundedCornerShape(18.dp),
//            minLines = minLines,
//            maxLines = maxLines,
//            keyboardOptions = keyboardOptions
//        )
    }
}
