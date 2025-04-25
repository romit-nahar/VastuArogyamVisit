package com.example.vastuarogyamvisit.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Enhanced text field with validation support
 *
 * @param value Current text value
 * @param onValueChange Callback when value changes
 * @param label Label text
 * @param modifier Modifier for styling
 * @param isError Whether the field has an error
 * @param errorMessage Error message to display
 * @param maxLines Maximum number of lines
 * @param leadingIcon Optional leading icon composable
 */
@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    maxLines: Int = 1,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            maxLines = maxLines,
            singleLine = maxLines == 1,
            leadingIcon = leadingIcon,
            trailingIcon = {
                if (isError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                } else if (value.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Valid",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )

        AnimatedVisibility(visible = isError && !errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}