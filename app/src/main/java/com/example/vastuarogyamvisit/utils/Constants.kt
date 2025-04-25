package com.example.vastuarogyamvisit.utils

/**
 * Constants for report types and other dropdown options
 */
object DropdownConstants {
    // Report Types
    val REPORT_TYPES = listOf(
        "Site Analysis Report",
        "Home Inspection Report",
        "Property Evaluation Report",
        "Energy Assessment Report",
        "Safety Compliance Report"
    )

    // Inspection Categories
    val INSPECTION_CATEGORIES = listOf(
        "Structural",
        "Electrical",
        "Plumbing",
        "Interior",
        "Exterior"
    )

    // Property Types
    val PROPERTY_TYPES = listOf(
        "Residential",
        "Commercial",
        "Industrial",
        "Mixed Use",
        "Vacant Land"
    )

    // Visit Purposes
    val VISIT_PURPOSES = listOf(
        "Initial Assessment",
        "Follow-up Check",
        "Client Request",
        "Regulatory Compliance",
        "Final Inspection"
    )
}

/**
 * Constants for file paths and naming
 */
object FileConstants {
    const val PDF_DIRECTORY = "VastuArogyam/Reports"
    const val IMAGE_DIRECTORY = "VastuArogyam/Images"
    const val PDF_FILE_PREFIX = "report_"
    const val IMAGE_FILE_PREFIX = "img_"
}

/**
 * Constants for permission requests
 */
object PermissionConstants {
    const val CAMERA_PERMISSION_REQUEST = 100
    const val STORAGE_PERMISSION_REQUEST = 101
    const val CAMERA_AND_STORAGE_REQUEST = 102
}