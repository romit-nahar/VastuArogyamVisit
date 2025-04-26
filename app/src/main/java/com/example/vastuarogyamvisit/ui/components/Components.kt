package com.example.vastuarogyamvisit.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vastuarogyamvisit.ui.theme.VastuArogyamVisitTheme
import com.example.vastuarogyamvisit.utils.DropdownConstants
import com.example.vastuarogyamvisit.utils.FontConstants
import com.example.vastuarogyamvisit.utils.VastuDropdownConstants

/**
 * Reusable dropdown component for multiple selection types
 *
 * @param label The dropdown label
 * @param options List of options to display
 * @param selectedOption Currently selected option
 * @param onOptionSelected Callback when an option is selected
 * @param isDevanagari Whether to use Devanagari font
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelector(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    isDevanagari: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }

    // Get the Devanagari font
    val context = LocalContext.current
    val devanagariFont = try {
        FontFamily(
            Font(
                assetManager = context.assets,
                path = FontConstants.DEVANAGARI_FONT_PATH,
                weight = FontWeight.Normal,
                style = FontStyle.Normal
            )
        )
    } catch (e: Exception) {
        null
    }

    Column(modifier = modifier) {
        // Bold and padded label
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = modifier
        ) {
            TextField(
                readOnly = true,
                value = selectedOption,
                onValueChange = {},
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                // Apply Devanagari font if needed and available
                textStyle = if (isDevanagari && devanagariFont != null) {
                    androidx.compose.ui.text.TextStyle(fontFamily = devanagariFont)
                } else {
                    androidx.compose.ui.text.TextStyle()
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                // Apply Devanagari font if needed and available
                                fontFamily = if (isDevanagari && devanagariFont != null) {
                                    devanagariFont
                                } else {
                                    null
                                }
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownSelectorPreview() {
    VastuArogyamVisitTheme {
        DropdownSelector(
            label = "वास्तुभूमिदोष",
            options = VastuDropdownConstants.VASTUBHOOMIDOSH,
            selectedOption = VastuDropdownConstants.VASTUBHOOMIDOSH[0],
            onOptionSelected = {},
            isDevanagari = true
        )
    }
}