package com.example.vastuarogyamvisit

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vastuarogyamvisit.model.VisitFormData
import com.example.vastuarogyamvisit.ui.components.*
import java.io.File
import com.example.vastuarogyamvisit.utils.VastuDropdownConstants

@Composable
fun SiteInformationSection(formState: FormState) {
    BeautifulCard(title = "Site Information") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulTextField(
                    value = formState.name,
                    onValueChange = { formState.name = it },
                    label = "Name",
                    modifier = Modifier.weight(1f)
                )

                BeautifulTextField(
                    value = formState.email,
                    onValueChange = { formState.email = it },
                    label = "Email",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulTextField(
                    value = formState.phone,
                    onValueChange = { formState.phone = it },
                    label = "Phone",
                    modifier = Modifier.weight(1f)
                )

                BeautifulTextField(
                    value = formState.area,
                    onValueChange = { formState.area = it },
                    label = "Area",
                    modifier = Modifier.weight(1f)
                )
            }

            BeautifulTextField(
                value = formState.address,
                onValueChange = { formState.address = it },
                label = "Address",
                isMultiline = true
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulTextField(
                    value = formState.north,
                    onValueChange = { formState.north = it },
                    label = "North",
                    modifier = Modifier.weight(1f)
                )

                BeautifulTextField(
                    value = formState.road,
                    onValueChange = { formState.road = it },
                    label = "Road",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulTextField(
                    value = formState.open,
                    onValueChange = { formState.open = it },
                    label = "Open",
                    modifier = Modifier.weight(1f)
                )

                BeautifulTextField(
                    value = formState.close,
                    onValueChange = { formState.close = it },
                    label = "Close",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulTextField(
                    value = formState.startedYear,
                    onValueChange = { formState.startedYear = it },
                    label = "Started Year",
                    modifier = Modifier.weight(1f)
                )

                BeautifulTextField(
                    value = formState.extYear,
                    onValueChange = { formState.extYear = it },
                    label = "Extension Year",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DowsingSection(formState: FormState) {
    BeautifulCard(title = "Dowsing Details") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Spandan dropdown
            SignPercentageDropdownSelector(
                label = "स्पंदन",
                selectedSign = formState.spandan.take(1),
                onSignSelected = { formState.spandan = it + formState.spandan.drop(1) },
                selectedPercentage = formState.spandan.drop(1),
                onPercentageSelected = { formState.spandan = formState.spandan.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Rahnyas dropdown
            BeautifulDropdownSelector(
                label = "राहण्यास योग्य",
                options = VastuDropdownConstants.RAHNYAS_YOGYA,
                selectedOption = formState.rahnyasYogya,
                onOptionSelected = { formState.rahnyasYogya = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Vastubhoomidosh dropdown
            BeautifulDropdownSelector(
                label = "वास्तुभूमिदोष",
                options = VastuDropdownConstants.VASTUBHOOMIDOSH,
                selectedOption = formState.vastuBhoomiDosh,
                onOptionSelected = { formState.vastuBhoomiDosh = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Shalya dropdown
            BeautifulDropdownSelector(
                label = "शल्य दोष",
                options = VastuDropdownConstants.SHALYA_DOSH,
                selectedOption = formState.shalyaDosh,
                onOptionSelected = { formState.shalyaDosh = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Entity Dropdown
            BeautifulDropdownSelector(
                label = "Entity",
                options = VastuDropdownConstants.ENTITY,
                selectedOption = formState.entity,
                onOptionSelected = { formState.entity = it },
                modifier = Modifier.fillMaxWidth()
            )

            // GS Dropdown
            BeautifulDropdownSelector(
                label = "GS",
                options = VastuDropdownConstants.GS,
                selectedOption = formState.gs,
                onOptionSelected = { formState.gs = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Maanviya Dosh Dropdown
            BeautifulDropdownSelector(
                label = "मानवीय दोष",
                options = VastuDropdownConstants.MAANVIYA_DOSH,
                selectedOption = formState.maanviyaDosh,
                onOptionSelected = { formState.maanviyaDosh = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Amaanviya Dosh Dropdown
            BeautifulDropdownSelector(
                label = "अमानवीय दोष",
                options = VastuDropdownConstants.AMAANVIYA_DOSH,
                selectedOption = formState.amaanviyaDosh,
                onOptionSelected = { formState.amaanviyaDosh = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Gharatil Vastavya Spandane Dropdown
            BeautifulDropdownSelector(
                label = "घरातील वास्तव्य स्पंदने",
                options = VastuDropdownConstants.GHARATIL_VASTAVYA_SPANDANE,
                selectedOption = formState.gharatilVastavyaSpandane,
                onOptionSelected = { formState.gharatilVastavyaSpandane = it },
                modifier = Modifier.fillMaxWidth()
            )

            // Jastit Jast Kamit Kamii Dosh Dropdown
            BeautifulDropdownSelector(
                label = "जास्तीत जास्त, कमीत कमी दोष",
                options = VastuDropdownConstants.JASTIT_JAST_KAMIT_KAMI_DOSH,
                selectedOption = formState.jastitJastKamitKamiDosh,
                onOptionSelected = { formState.jastitJastKamitKamiDosh = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MicroEnergiesSection(formState: FormState) {
    BeautifulCard(title = "Micro Energies") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dhan Aakarshan percentage
            SignPercentageDropdownSelector(
                label = "धनआकर्षण",
                selectedSign = formState.dhanaakarshan.take(1),
                onSignSelected = { formState.dhanaakarshan = it + formState.dhanaakarshan.drop(1) },
                selectedPercentage = formState.dhanaakarshan.drop(1),
                onPercentageSelected = { formState.dhanaakarshan = formState.dhanaakarshan.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Sukh percentage
            SignPercentageDropdownSelector(
                label = "सुख",
                selectedSign = formState.sukh.take(1),
                onSignSelected = { formState.sukh = it + formState.sukh.drop(1) },
                selectedPercentage = formState.sukh.drop(1),
                onPercentageSelected = { formState.sukh = formState.sukh.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Aarogya percentage
            SignPercentageDropdownSelector(
                label = "आरोग्य",
                selectedSign = formState.aarogya.take(1),
                onSignSelected = { formState.aarogya = it + formState.aarogya.drop(1) },
                selectedPercentage = formState.aarogya.drop(1),
                onPercentageSelected = { formState.aarogya = formState.aarogya.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Aishwary percentage
            SignPercentageDropdownSelector(
                label = "ऐश्वर्य",
                selectedSign = formState.aishwary.take(1),
                onSignSelected = { formState.aishwary = it + formState.aishwary.drop(1) },
                selectedPercentage = formState.aishwary.drop(1),
                onPercentageSelected = { formState.aishwary = formState.aishwary.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Yashmatsar percentage
            SignPercentageDropdownSelector(
                label = "यश मत्सर",
                selectedSign = formState.yashmatsar.take(1),
                onSignSelected = { formState.yashmatsar = it + formState.yashmatsar.drop(1) },
                selectedPercentage = formState.yashmatsar.drop(1),
                onPercentageSelected = { formState.yashmatsar = formState.yashmatsar.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Grahak Aakarshan percentage
            SignPercentageDropdownSelector(
                label = "ग्राहक आकर्षण",
                selectedSign = formState.grahakAakarshan.take(1),
                onSignSelected = { formState.grahakAakarshan = it + formState.grahakAakarshan.drop(1) },
                selectedPercentage = formState.grahakAakarshan.drop(1),
                onPercentageSelected = { formState.grahakAakarshan = formState.grahakAakarshan.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Grahak Samadhan percentage
            SignPercentageDropdownSelector(
                label = "ग्राहक समाधान",
                selectedSign = formState.grahakSamadhan.take(1),
                onSignSelected = { formState.grahakSamadhan = it + formState.grahakSamadhan.drop(1) },
                selectedPercentage = formState.grahakSamadhan.drop(1),
                onPercentageSelected = { formState.grahakSamadhan = formState.grahakSamadhan.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Paraspar Sambandh percentage
            SignPercentageDropdownSelector(
                label = "परस्पर संबंध",
                selectedSign = formState.parasparSambandh.take(1),
                onSignSelected = { formState.parasparSambandh = it + formState.parasparSambandh.drop(1) },
                selectedPercentage = formState.parasparSambandh.drop(1),
                onPercentageSelected = { formState.parasparSambandh = formState.parasparSambandh.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Annapurna percentage
            SignPercentageDropdownSelector(
                label = "अन्नपूर्णा",
                selectedSign = formState.annapurna.take(1),
                onSignSelected = { formState.annapurna = it + formState.annapurna.drop(1) },
                selectedPercentage = formState.annapurna.drop(1),
                onPercentageSelected = { formState.annapurna = formState.annapurna.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )

            // Vansh Vriddhi percentage
            SignPercentageDropdownSelector(
                label = "वंशवृद्धी",
                selectedSign = formState.vanshVriddhi.take(1),
                onSignSelected = { formState.vanshVriddhi = it + formState.vanshVriddhi.drop(1) },
                selectedPercentage = formState.vanshVriddhi.drop(1),
                onPercentageSelected = { formState.vanshVriddhi = formState.vanshVriddhi.take(1) + it },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LecherAntennaSection(formState: FormState) {
    BeautifulCard(title = "Lecher Antenna Measurements") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulDropdownSelector(
                    label = "17.6",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher176,
                    onOptionSelected = { formState.lecher176 = it },
                    modifier = Modifier.weight(1f)
                )

                BeautifulDropdownSelector(
                    label = "3.3",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher33,
                    onOptionSelected = { formState.lecher33 = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulDropdownSelector(
                    label = "4.2",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher42,
                    onOptionSelected = { formState.lecher42 = it },
                    modifier = Modifier.weight(1f)
                )

                BeautifulDropdownSelector(
                    label = "6.6",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher66,
                    onOptionSelected = { formState.lecher66 = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulDropdownSelector(
                    label = "8.0",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher80,
                    onOptionSelected = { formState.lecher80 = it },
                    modifier = Modifier.weight(1f)
                )

                BeautifulDropdownSelector(
                    label = "8.6",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher86,
                    onOptionSelected = { formState.lecher86 = it },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BeautifulDropdownSelector(
                    label = "18.0",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher180,
                    onOptionSelected = { formState.lecher180 = it },
                    modifier = Modifier.weight(1f)
                )

                BeautifulDropdownSelector(
                    label = "13.0",
                    options = listOf("Positive", "Negative"),
                    selectedOption = formState.lecher130,
                    onOptionSelected = { formState.lecher130 = it },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun AdditionalNotesSection(formState: FormState) {
    BeautifulCard(title = "Additional Notes") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BeautifulTextField(
                value = formState.notes,
                onValueChange = { formState.notes = it },
                label = "Notes",
                isMultiline = true
            )
        }
    }
}

@Composable
fun ImageCaptureSection(
    capturedImage: Bitmap?,
    onCaptureImage: () -> Unit
) {
    BeautifulCard(title = "Site Image") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (capturedImage != null) {
                ImagePreview(
                    bitmap = capturedImage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            BeautifulButton(
                text = "Capture Image",
                onClick = onCaptureImage
            )
        }
    }
}

@Composable
fun PdfGenerationSection(
    formState: FormState,
    generatedPdfFile: File?,
    isGeneratingPdf: Boolean,
    onGeneratePdf: (VisitFormData) -> Unit,
    onOpenPdf: (File) -> Unit,
    onSharePdf: (File) -> Unit
) {
    BeautifulCard(title = "PDF Generation") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BeautifulButton(
                text = if (isGeneratingPdf) "Generating PDF..." else "Generate PDF",
                onClick = { onGeneratePdf(formState.createVisitFormData()) },
                isEnabled = !isGeneratingPdf
            )

            if (generatedPdfFile != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BeautifulButton(
                        text = "Open PDF",
                        onClick = { onOpenPdf(generatedPdfFile) },
                        modifier = Modifier.weight(1f)
                    )

                    BeautifulButton(
                        text = "Share PDF",
                        onClick = { onSharePdf(generatedPdfFile) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
} 