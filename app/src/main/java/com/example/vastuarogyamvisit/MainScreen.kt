package com.example.vastuarogyamvisit

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vastuarogyamvisit.model.VisitFormData
import com.example.vastuarogyamvisit.ui.components.*
import java.io.File

@Composable
fun MainScreen(
    capturedImage: Bitmap?,
    generatedPdfFile: File?,
    isGeneratingPdf: Boolean,
    onCaptureImage: () -> Unit,
    onGeneratePdf: (VisitFormData) -> Unit,
    onOpenPdf: (File) -> Unit,
    onSharePdf: (File) -> Unit
) {
    // Form state
    val formState = rememberFormState()

    // Scrollable content
    val scrollState = rememberScrollState()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Header/Logo
            AppHeader()

            // Contact Information Card
            SiteInformationSection(
                formState = formState
            )

            // Dowsing Details Card
            DowsingSection(
                formState = formState
            )

            // Micro Energies Card
            MicroEnergiesSection(
                formState = formState
            )

            // Lecher Antenna Card
            LecherAntennaSection(
                formState = formState
            )

            // Additional Notes Card
            AdditionalNotesSection(
                formState = formState
            )

            // Image Capture Card
            ImageCaptureSection(
                capturedImage = capturedImage,
                onCaptureImage = onCaptureImage
            )

            // PDF Generation Card
            PdfGenerationSection(
                formState = formState,
                generatedPdfFile = generatedPdfFile,
                isGeneratingPdf = isGeneratingPdf,
                onGeneratePdf = onGeneratePdf,
                onOpenPdf = onOpenPdf,
                onSharePdf = onSharePdf
            )

            // Add some space at the bottom for better scrolling experience
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Vastu Arogyam",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                "Energy Inspection Report",
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
} 