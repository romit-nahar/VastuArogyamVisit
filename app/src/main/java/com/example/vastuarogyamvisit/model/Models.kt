package com.example.vastuarogyamvisit.model

import android.graphics.Bitmap
import java.io.Serializable

/**
 * Data class representing user input form data
 */
data class VisitFormData(
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val reportType: String = "",
    val propertyType: String = "",
    val inspectionCategory: String = "",
    val visitPurpose: String = "",
    val notes: String = "",
    var capturedImage: Bitmap? = null
) : Serializable {
    // Serializable interface doesn't work directly with Bitmap
    // This is just for the structure, in practice we'd handle this differently
    // such as storing file paths instead of Bitmap objects
}