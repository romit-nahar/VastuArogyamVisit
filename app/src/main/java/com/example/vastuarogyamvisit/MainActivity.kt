package com.example.vastuarogyamvisit

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.vastuarogyamvisit.ui.theme.VastuArogyamVisitTheme
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image as PdfImage
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ComponentActivity() {
    private var photoFile: File? = null
    private var capturedImage: Bitmap? by mutableStateOf(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VastuArogyamVisitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        requestPermissions()
    }

    @Composable
    fun MainContent(modifier: Modifier = Modifier) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var phone by remember { mutableStateOf("") }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Enter details", modifier = Modifier.align(Alignment.Start))

            // Name input
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            // Email input
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            // Phone input
            TextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )

            // Capture Image Button
            Button(onClick = { openCamera() }) {
                Text("Capture Image")
            }

            // Display captured image
            capturedImage?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Captured Image",
                    modifier = Modifier.size(200.dp)
                )
            }

            // Generate PDF button
            Button(onClick = { generatePdf(name, email, phone, capturedImage) }) {
                Text("Generate PDF")
            }
        }
    }

    private fun openCamera() {
        try {
            // Create an intent to capture an image
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            // Create a temporary file to store the image
            photoFile = createImageFile()

            // Continue only if the file was created successfully
            photoFile?.let { file ->
                val photoUri = FileProvider.getUriForFile(
                    this,
                    "com.example.vastuarogyamvisit.provider",
                    file
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                captureImageLauncher.launch(intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error opening camera: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private val captureImageLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                photoFile?.let { file ->
                    // Convert photoFile to Uri using FileProvider
                    val photoUri = FileProvider.getUriForFile(
                        this,
                        "com.example.vastuarogyamvisit.provider",
                        file
                    )
                    capturedImage = MediaStore.Images.Media.getBitmap(contentResolver, photoUri)
                }
            }
        }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
    }

    // PDF generation logic
    private fun generatePdf(name: String, email: String, phone: String, image: Bitmap?) {
        try {
            val pdfFile = File(getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "user_data.pdf")
            val pdfWriter = PdfWriter(pdfFile)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            // Add text
            document.add(Paragraph("Name: $name"))
            document.add(Paragraph("Email: $email"))
            document.add(Paragraph("Phone: $phone"))

            // Add image if available
            image?.let {
                val imageData = ImageDataFactory.create(it.toByteArray())
                val pdfImage = PdfImage(imageData).apply {
                    scaleToFit(200f, 200f)
                    setTextAlignment(TextAlignment.CENTER)
                }
                document.add(pdfImage)
            }

            document.close()

            Toast.makeText(this, "PDF Created", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error creating PDF", Toast.LENGTH_SHORT).show()
        }
    }

    private fun Bitmap.toByteArray(): ByteArray {
        val byteArrayOutputStream = java.io.ByteArrayOutputStream()
        this.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }

    private fun requestPermissions() {
        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                100
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    VastuArogyamVisitTheme {
//        MainContent()
//    }
//}