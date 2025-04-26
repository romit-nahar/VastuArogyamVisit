package com.example.vastuarogyamvisit.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.widget.Toast
import com.example.vastuarogyamvisit.model.VisitFormData
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image as PdfImage
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class for PDF operations
 */
object PdfUtils {

    /**
     * Generates a PDF document with the visit form data
     *
     * @param context The application context
     * @param formData The form data to include in the PDF
     * @return File object pointing to the generated PDF, or null if generation failed
     */
    fun generatePdf(context: Context, formData: VisitFormData): File? {
        try {
            // Create timestamp for unique filename
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
            val fileName = "${FileConstants.PDF_FILE_PREFIX}${timestamp}.pdf"

            // Create directory if it doesn't exist
            val storageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                FileConstants.PDF_DIRECTORY
            )
            if (!storageDir.exists()) {
                storageDir.mkdirs()
            }

            // Create file
            val pdfFile = File(storageDir, fileName)
            val pdfWriter = PdfWriter(pdfFile)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)

            // Load Devanagari font for Marathi text
            val devanagariFont = try {
                val fontStream = context.assets.open(FontConstants.DEVANAGARI_FONT_PATH)
                val fontData = fontStream.readBytes()
                fontStream.close()
                PdfFontFactory.createFont(fontData, PdfEncodings.IDENTITY_H, true)
            } catch (e: Exception) {
                // If font fails to load, use default font
                null
            }

            // Add title
            val title = Paragraph("Vastu Arogyam Visit Report")
                .setFontSize(18f)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
            document.add(title)

            // Add date
            val dateText = Paragraph("Generated on: ${SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(Date())}")
                .setFontSize(10f)
                .setItalic()
                .setTextAlignment(TextAlignment.RIGHT)
            document.add(dateText)

            document.add(Paragraph("\n"))

            // Create table for form data
            val table = Table(UnitValue.createPercentArray(floatArrayOf(30f, 70f)))
                .setWidth(UnitValue.createPercentValue(100f))

            // Add rows to table
            addTableRow(table, "Name", formData.name, devanagariFont)
            addTableRow(table, "Email", formData.email, devanagariFont)
            addTableRow(table, "Phone", formData.phone, devanagariFont)

            // Dowsing
            addTableRow(table, "वास्तुतील स्पंदने", formData.spandan, devanagariFont, true)
            addTableRow(table, "राहण्यास योग्य", formData.rahnyasYogya, devanagariFont, true)
            addTableRow(table, "वास्तुभूमिदोष", formData.vastuBhoomiDosh, devanagariFont, true)
            addTableRow(table, "शल्य दोष", formData.shalyaDosh, devanagariFont, true)
            addTableRow(table, "Entity", formData.entity, devanagariFont, true)
            // GS
            addTableRow(table, "मानवीय दोष", formData.maanviyaDosh, devanagariFont, true)
            addTableRow(table, "अमानवीय दोष", formData.amaanviyaDosh, devanagariFont, true)
            addTableRow(table, "घरातीत वास्तव्य स्पंदने", formData.gharatilVastavyaSpandane, devanagariFont, true)
            addTableRow(table, "जास्तीत जास्त/कमीत कमी दोष", formData.jastitJastKamitKamiDosh, devanagariFont, true)

            // Micro Energies


            addTableRow(table, "Notes", formData.notes, devanagariFont)

            document.add(table)

            // Add image if available
            formData.capturedImage?.let {
                document.add(Paragraph("\n"))
                document.add(Paragraph("Site Image:").setBold())

                val imageData = ImageDataFactory.create(it.toByteArray())
                val pdfImage = PdfImage(imageData).apply {
                    scaleToFit(300f, 300f)
                    setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.CENTER)
                }
                document.add(pdfImage)
            }

            document.close()
            return pdfFile

        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * Adds a row to the table with label and value
     *
     * @param table The table to add the row to
     * @param label The row label
     * @param value The row value
     * @param devanagariFont Optional Devanagari font for Marathi text
     * @param useDevanagari Whether to use Devanagari font for this row
     */
    private fun addTableRow(table: Table, label: String, value: String, devanagariFont: PdfFont? = null, useDevanagari: Boolean = false) {
        val labelParagraph = Paragraph(label).setBold()
        val valueParagraph = Paragraph(value)

        // Apply Devanagari font if available and required
        if (useDevanagari && devanagariFont != null) {
            labelParagraph.setFont(devanagariFont)
            valueParagraph.setFont(devanagariFont)
        }

        val labelCell = Cell().add(labelParagraph)
        val valueCell = Cell().add(valueParagraph)

        table.addCell(labelCell)
        table.addCell(valueCell)
    }

    /**
     * Converts a Bitmap to ByteArray
     *
     * @return ByteArray representation of the Bitmap
     */
    private fun Bitmap.toByteArray(): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}