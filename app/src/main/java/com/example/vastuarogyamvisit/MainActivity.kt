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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.vastuarogyamvisit.model.VisitFormData
import com.example.vastuarogyamvisit.ui.components.DropdownSelector
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
                        onCaptureImage = { openCamera() },
                        onGeneratePdf = { formData ->
                            formData.capturedImage = capturedImage
                            generatePdf(formData)
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
        val pdfFile = PdfUtils.generatePdf(this, formData)

        if (pdfFile != null) {
            generatedPdfFile = pdfFile
            Toast.makeText(
                this,
                "PDF Created: ${pdfFile.name}",
                Toast.LENGTH_LONG
            ).show()

            // Optionally open the PDF
            openPdf(pdfFile)
        } else {
            Toast.makeText(this, "Error creating PDF", Toast.LENGTH_SHORT).show()
        }
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
    onCaptureImage: () -> Unit,
    onGeneratePdf: (VisitFormData) -> Unit,
    onSharePdf: (File) -> Unit
) {
    // Form state
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

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
        Text(
            text = "Vastu Arogyam Visit Form",
            modifier = Modifier.align(Alignment.Start)
        )

        // Basic information fields
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        // Dropdown selectors
        Text(
            text = "Select Report Details:",
            modifier = Modifier.align(Alignment.Start)
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

        Spacer(modifier = Modifier.height(16.dp))

        // Capture Image Button
        Button(
            onClick = onCaptureImage,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Capture Image")
        }

        // Display captured image
        capturedImage?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        // Generate PDF button
        Button(
            onClick = {
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
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Generate PDF")
        }

        generatedPdfFile?.let { pdfFile ->
            Button(
                onClick = { onSharePdf(pdfFile) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Share PDF")
            }
        }

        // Add some space at the bottom for better scrolling experience
        Spacer(modifier = Modifier.height(16.dp))
    }
}