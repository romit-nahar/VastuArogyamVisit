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
    // Dowsing
    val spandan: String = "",
    val rahnyasYogya: String = "",
    val vastuBhoomiDosh: String = "",
    val shalyaDosh: String = "",
    val gs: String = "",
    val entity: String = "",
    val maanviyaDosh: String = "",
    val amaanviyaDosh: String = "",
    val gharatilVastavyaSpandane: String = "",
    val jastitJastKamitKamiDosh: String = "",

    // Micro Energies
    val dhanaakarshan: String = "",
    val sukh: String = "",
    val aarogya: String = "",
    val aishwary: String = "",
    val yashmatsar: String = "",
    val grahakAakarshan: String = "",
    val grahakSamadhan: String = "",
    val parasparSambandh: String = "",

    val notes: String = "",
    var capturedImage: Bitmap? = null
    ) : Serializable {
    // Serializable interface doesn't work directly with Bitmap
    // This is just for the structure, in practice we'd handle this differently
    // such as storing file paths instead of Bitmap objects
    }