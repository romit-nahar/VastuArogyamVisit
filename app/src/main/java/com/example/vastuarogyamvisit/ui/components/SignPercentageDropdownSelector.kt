package com.example.vastuarogyamvisit.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vastuarogyamvisit.utils.VastuDropdownConstants.PERCENTAGE

@Composable
fun SignPercentageDropdownSelector(
    label: String,
    selectedSign: String,
    onSignSelected: (String) -> Unit,
    selectedPercentage: String,
    onPercentageSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(label, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Sign dropdown (+ / -)
            SignToggleSelector(
                selectedSign = selectedSign,
                onSignSelected = onSignSelected,
                modifier = Modifier.weight(1f)
            )

            // Percentage dropdown (0%, 5%, ..., 100%)
            DropdownSelector(
                label = "Percentage",
                options = PERCENTAGE,
                selectedOption = selectedPercentage,
                onOptionSelected = onPercentageSelected,
                modifier = Modifier.weight(2f)
            )
        }
    }
}