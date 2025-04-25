package com.example.vastuarogyamvisit

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.vastuarogyamvisit.model.VisitFormData
import com.example.vastuarogyamvisit.ui.components.DropdownSelector
import com.example.vastuarogyamvisit.ui.components.ValidatedTextField
import com.example.vastuarogyamvisit.ui.theme.VastuArogyamVisitTheme
import com.example.vastuarogyamvisit.utils.DropdownConstants
import com.example.vastuarogyamvisit.utils.FileConstants
import com.example.vastuarogyamvisit.utils.PdfUtils
import com.example.vastuarogyamvisit.utils.PermissionConstants
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Main activity for the Vastu Arogyam Visit app
 */
class MainActivity : ComponentActivity() {
    private val TAG = "MainActivity"

    // State variables
    private var photoFile: File? = null
    private var capturedImage: Bitmap? by mutableStateOf(null)
    private var generatedPdfFile: File? by mutableStateOf(null)
    private var isGeneratingPdf: Boolean by mutableStateOf(false)

    // Permission launcher
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }
        if (allGranted) {
            Log.d(TAG, "All permissions granted")
        } else {
            Toast.makeText(
                this,
                "Permissions are necessary for full app functionality",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Camera launcher
    private val captureImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            photoFile?.let { file ->
                try {
                    val photoUri = FileProvider.getUriForFile(
                        this,
                        "${packageName}.provider",
                        file
                    )
                    capturedImage = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
                } catch (e: Exception) {
                    Log.e(TAG, "Error processing camera image: ${e.message}")
                    Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request necessary permissions
        requestPermissions()

        setContent {
            VastuArogyamVisitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding),
                        capturedImage = capturedImage,
                        generatedPdfFile = generatedPdfFile,
                        isGeneratingPdf = isGeneratingPdf,
                        onCaptureImage = { openCamera() },
                        onGeneratePdf = { formData ->
                            formData.capturedImage = capturedImage
                            generatePdf(formData)
                        },
                        onOpenPdf = { file ->
                            openPdf(file)
                        },
                        onSharePdf = { file ->
                            sharePdf(file)
                        }
                    )
                }
            }
        }
    }

    /**
     * Opens the camera to capture an image
     */
    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
            return
        }

        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // Create temporary file to store the image
            photoFile = createImageFile()

            photoFile?.let { file ->
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "${packageName}.provider",
                    file
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                captureImageLauncher.launch(intent)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error opening camera: ${e.message}")
            Toast.makeText(this, "Error opening camera", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Creates a temporary file for storing the captured image
     *
     * @return File object for the temporary image
     */
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir = File(
            getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            FileConstants.IMAGE_DIRECTORY
        )

        if (!storageDir.exists()) {
            storageDir.mkdirs()
        }

        return File(storageDir, "${FileConstants.IMAGE_FILE_PREFIX}${timeStamp}.jpg")
    }

    /**
     * Generates a PDF from the form data
     *
     * @param formData The form data to include in the PDF
     */
    private fun generatePdf(formData: VisitFormData) {
        isGeneratingPdf = true

        Thread {
            val pdfFile = PdfUtils.generatePdf(this, formData)

            runOnUiThread {
                isGeneratingPdf = false

                if (pdfFile != null) {
                    generatedPdfFile = pdfFile
                    Toast.makeText(
                        this,
                        "PDF Created: ${pdfFile.name}",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this, "Error creating PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    /**
     * Opens a PDF file with an external app
     *
     * @param file The PDF file to open
     */
    private fun openPdf(file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "No PDF viewer app found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error opening PDF: ${e.message}")
            Toast.makeText(this, "Error opening PDF", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Shares a PDF file with other apps
     *
     * @param file The PDF file to share
     */
    private fun sharePdf(file: File) {
        try {
            val uri = FileProvider.getUriForFile(
                this,
                "${packageName}.provider",
                file
            )

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "application/pdf"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            val chooserIntent = Intent.createChooser(shareIntent, "Share PDF Report")
            startActivity(chooserIntent)
        } catch (e: Exception) {
            Log.e(TAG, "Error sharing PDF: ${e.message}")
            Toast.makeText(this, "Error sharing PDF", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Requests necessary permissions for the app
     */
    private fun requestPermissions() {
        val permissions = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(android.Manifest.permission.CAMERA)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && android.os.Build.VERSION.SDK_INT <= 32
        ) {
            permissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED && android.os.Build.VERSION.SDK_INT <= 32
        ) {
            permissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissions.isNotEmpty()) {
            requestPermissionLauncher.launch(permissions.toTypedArray())
        }
    }
}

/**
 * Main composable screen for the app
 */
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    capturedImage: Bitmap?,
    generatedPdfFile: File?,
    isGeneratingPdf: Boolean,
    onCaptureImage: () -> Unit,
    onGeneratePdf: (VisitFormData) -> Unit,
    onOpenPdf: (File) -> Unit,
    onSharePdf: (File) -> Unit
) {
    // Form state
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    // Field validation state
    var nameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    // Dropdown selections
    var selectedReportType by remember {
        mutableStateOf(DropdownConstants.REPORT_TYPES.first())
    }
    var selectedPropertyType by remember {
        mutableStateOf(DropdownConstants.PROPERTY_TYPES.first())
    }
    var selectedInspectionCategory by remember {
        mutableStateOf(DropdownConstants.INSPECTION_CATEGORIES.first())
    }
    var selectedVisitPurpose by remember {
        mutableStateOf(DropdownConstants.VISIT_PURPOSES.first())
    }

    // Scrollable content
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // App Header/Logo
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
                    "Property Inspection Reports",
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        // Contact Information Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Contact Information",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                ValidatedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = it.isEmpty()
                    },
                    label = "Name",
                    isError = nameError,
                    errorMessage = if (nameError) "Name is required" else null,
                    modifier = Modifier.fillMaxWidth()
                )

                ValidatedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !it.contains("@") && it.isNotEmpty()
                    },
                    label = "Email",
                    isError = emailError,
                    errorMessage = if (emailError) "Enter a valid email address" else null,
                    modifier = Modifier.fillMaxWidth()
                )

                ValidatedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = it.isNotEmpty() && !it.all { char -> char.isDigit() }
                    },
                    label = "Phone Number",
                    isError = phoneError,
                    errorMessage = if (phoneError) "Phone should contain only digits" else null,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Report Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "Report Details",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Report Type dropdown
                DropdownSelector(
                    label = "Report Type",
                    options = DropdownConstants.REPORT_TYPES,
                    selectedOption = selectedReportType,
                    onOptionSelected = { selectedReportType = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // Property Type dropdown
                DropdownSelector(
                    label = "Property Type",
                    options = DropdownConstants.PROPERTY_TYPES,
                    selectedOption = selectedPropertyType,
                    onOptionSelected = { selectedPropertyType = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // Inspection Category dropdown
                DropdownSelector(
                    label = "Inspection Category",
                    options = DropdownConstants.INSPECTION_CATEGORIES,
                    selectedOption = selectedInspectionCategory,
                    onOptionSelected = { selectedInspectionCategory = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // Visit Purpose dropdown
                DropdownSelector(
                    label = "Visit Purpose",
                    options = DropdownConstants.VISIT_PURPOSES,
                    selectedOption = selectedVisitPurpose,
                    onOptionSelected = { selectedVisitPurpose = it },
                    modifier = Modifier.fillMaxWidth()
                )

                // Notes field
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Additional Notes") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )
            }
        }

        // Image Capture Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Property Image",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                AnimatedVisibility(
                    visible = capturedImage != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        capturedImage?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Captured Image",
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = onCaptureImage,
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("Retake Photo")
                            }
                        }
                    }
                }

                AnimatedVisibility(
                    visible = capturedImage == null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.outline,
                                    shape = RoundedCornerShape(8.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Camera",
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("No image captured")
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = onCaptureImage,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PhotoCamera,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Capture Image")
                            }
                        }
                    }
                }
            }
        }

        // PDF Generation Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Report Generation",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Success message when PDF is generated
                AnimatedVisibility(
                    visible = generatedPdfFile != null,
                    enter = fadeIn() + expandVertically()
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Success",
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "PDF Report Created Successfully!",
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }

                if (isGeneratingPdf) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Generating PDF...")
                    }
                } else {
                    Button(
                        onClick = {
                            // Simple form validation
                            nameError = name.isEmpty()
                            emailError = email.contains("@").not() && email.isNotEmpty()
                            phoneError = phone.isNotEmpty() && !phone.all { it.isDigit() }

                            if (!nameError && !emailError && !phoneError) {
                                val formData = VisitFormData(
                                    name = name,
                                    email = email,
                                    phone = phone,
                                    reportType = selectedReportType,
                                    propertyType = selectedPropertyType,
                                    inspectionCategory = selectedInspectionCategory,
                                    visitPurpose = selectedVisitPurpose,
                                    notes = notes
                                )
                                onGeneratePdf(formData)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generate PDF Report")
                        }
                    }

                    // PDF Action Buttons
                    generatedPdfFile?.let { pdfFile ->
                        Spacer(modifier = Modifier.height(8.dp))

                        Row {
                            Button(
                                onClick = { onOpenPdf(pdfFile) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Visibility,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("View")
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Button(
                                onClick = { onSharePdf(pdfFile) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Share")
                                }
                            }
                        }
                    }
                }
            }
        }

        // Add some space at the bottom for better scrolling experience
        Spacer(modifier = Modifier.height(16.dp))
    }
}