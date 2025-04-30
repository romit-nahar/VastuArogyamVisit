package com.example.vastuarogyamvisit.model

import android.graphics.Bitmap
import java.io.Serializable

/**
 * Data class representing user input form data
 */
data class VisitFormData(
    // Site Information
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val area: String = "",
    val north: String = "",
    val open: String = "",
    val close: String = "",
    val road: String = "",
    val startedYear: String = "",
    val extYear: String = "",

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
    val annapurna: String = "",
    val vanshVriddhi: String = "",

    // Lecher Antenna
    val lecher176: String = "Positive",
    val lecher33: String = "Positive",
    val lecher42: String = "Positive",
    val lecher66: String = "Positive",
    val lecher80: String = "Positive",
    val lecher86: String = "Positive",
    val lecher180: String = "Positive",
    val lecher130: String = "Positive",

    val notes: String = "",
    var capturedImage: Bitmap? = null
    ) : Serializable {
    // Serializable interface doesn't work directly with Bitmap
    // This is just for the structure, in practice we'd handle this differently
    // such as storing file paths instead of Bitmap objects
    }