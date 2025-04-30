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
            val fileName = "${formData.name}.pdf"

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
            addTableRow(table, "Address", formData.address, devanagariFont)
            addTableRow(table, "Area", formData.area, devanagariFont)
            addTableRow(table, "North", formData.north, devanagariFont)
            addTableRow(table, "Open", formData.open, devanagariFont)
            addTableRow(table, "Close", formData.close, devanagariFont)
            addTableRow(table, "Road", formData.road, devanagariFont)
            addTableRow(table, "Started Year", formData.startedYear, devanagariFont)
            addTableRow(table, "Extension Year", formData.extYear, devanagariFont)

            // Add Dowsing section
            document.add(Paragraph("\nDowsing").setBold())
            addTableRow(table, "स्पंदन", formData.spandan, devanagariFont, true)
            addTableRow(table, "राहण्यास योग्य", formData.rahnyasYogya, devanagariFont, true)
            addTableRow(table, "वास्तुभूमिदोष", formData.vastuBhoomiDosh, devanagariFont, true)
            addTableRow(table, "शल्य दोष", formData.shalyaDosh, devanagariFont, true)
            addTableRow(table, "Entity", formData.entity, devanagariFont)
            addTableRow(table, "GS", formData.gs, devanagariFont)
            addTableRow(table, "मानवीय दोष", formData.maanviyaDosh, devanagariFont, true)
            addTableRow(table, "अमानवीय दोष", formData.amaanviyaDosh, devanagariFont, true)
            addTableRow(table, "घरातील वास्तव्य स्पंदने", formData.gharatilVastavyaSpandane, devanagariFont, true)
            addTableRow(table, "जास्तीत जास्त, कमीत कमी दोष", formData.jastitJastKamitKamiDosh, devanagariFont, true)

            // Add Micro Energies section
            document.add(Paragraph("\nMicro Energies").setBold())
            addTableRow(table, "धनआकर्षण", formData.dhanaakarshan, devanagariFont, true)
            addTableRow(table, "सुख", formData.sukh, devanagariFont, true)
            addTableRow(table, "आरोग्य", formData.aarogya, devanagariFont, true)
            addTableRow(table, "ऐश्वर्य", formData.aishwary, devanagariFont, true)
            addTableRow(table, "यश मत्सर", formData.yashmatsar, devanagariFont, true)
            addTableRow(table, "ग्राहक आकर्षण", formData.grahakAakarshan, devanagariFont, true)
            addTableRow(table, "ग्राहक समाधान", formData.grahakSamadhan, devanagariFont, true)
            addTableRow(table, "परस्पर संबंध", formData.parasparSambandh, devanagariFont, true)
            addTableRow(table, "अन्नपूर्णा", formData.annapurna, devanagariFont, true)
            addTableRow(table, "वंशवृद्धी", formData.vanshVriddhi, devanagariFont, true)

            // Add Lecher Antenna section
            document.add(Paragraph("\nLecher Antenna").setBold())
            addTableRow(table, "17.6 MHz", formData.lecher176, devanagariFont)
            addTableRow(table, "3.3 MHz", formData.lecher33, devanagariFont)
            addTableRow(table, "4.2 MHz", formData.lecher42, devanagariFont)
            addTableRow(table, "6.6 MHz", formData.lecher66, devanagariFont)
            addTableRow(table, "8.0 MHz", formData.lecher80, devanagariFont)
            addTableRow(table, "8.6 MHz", formData.lecher86, devanagariFont)
            addTableRow(table, "18.0 MHz", formData.lecher180, devanagariFont)
            addTableRow(table, "13.0 MHz", formData.lecher130, devanagariFont)

            // Add Notes section
            document.add(Paragraph("\nAdditional Notes").setBold())
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