package com.example.vastuarogyamvisit

import androidx.compose.runtime.*
import com.example.vastuarogyamvisit.model.VisitFormData

class FormState {
    // Site Information
    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var area by mutableStateOf("")
    var north by mutableStateOf("")
    var open by mutableStateOf("")
    var close by mutableStateOf("")
    var road by mutableStateOf("")
    var startedYear by mutableStateOf("")
    var extYear by mutableStateOf("")

    // Dowsing
    var spandan by mutableStateOf("")
    var vastuBhoomiDosh by mutableStateOf("")
    var rahnyasYogya by mutableStateOf("")
    var shalyaDosh by mutableStateOf("")
    var entity by mutableStateOf("")
    var gs by mutableStateOf("")
    var maanviyaDosh by mutableStateOf("")
    var amaanviyaDosh by mutableStateOf("")
    var gharatilVastavyaSpandane by mutableStateOf("")
    var jastitJastKamitKamiDosh by mutableStateOf("")

    // Micro Energies
    var dhanaakarshan by mutableStateOf("")
    var sukh by mutableStateOf("")
    var aarogya by mutableStateOf("")
    var aishwary by mutableStateOf("")
    var yashmatsar by mutableStateOf("")
    var grahakAakarshan by mutableStateOf("")
    var grahakSamadhan by mutableStateOf("")
    var parasparSambandh by mutableStateOf("")
    var annapurna by mutableStateOf("")
    var vanshVriddhi by mutableStateOf("")

    // Lecher Antenna
    var lecher176 by mutableStateOf("Positive")
    var lecher33 by mutableStateOf("Positive")
    var lecher42 by mutableStateOf("Positive")
    var lecher66 by mutableStateOf("Positive")
    var lecher80 by mutableStateOf("Positive")
    var lecher86 by mutableStateOf("Positive")
    var lecher180 by mutableStateOf("Positive")
    var lecher130 by mutableStateOf("Positive")

    // Additional Notes
    var notes by mutableStateOf("")

    fun createVisitFormData(): VisitFormData {
        return VisitFormData(
            // Site Information
            name = name,
            email = email,
            phone = phone,
            address = address,
            area = area,
            north = north,
            open = open,
            close = close,
            road = road,
            startedYear = startedYear,
            extYear = extYear,

            // Dowsing
            spandan = spandan,
            vastuBhoomiDosh = vastuBhoomiDosh,
            rahnyasYogya = rahnyasYogya,
            shalyaDosh = shalyaDosh,
            entity = entity,
            gs = gs,
            maanviyaDosh = maanviyaDosh,
            amaanviyaDosh = amaanviyaDosh,
            gharatilVastavyaSpandane = gharatilVastavyaSpandane,
            jastitJastKamitKamiDosh = jastitJastKamitKamiDosh,

            // Micro Energies
            dhanaakarshan = dhanaakarshan,
            sukh = sukh,
            aarogya = aarogya,
            aishwary = aishwary,
            yashmatsar = yashmatsar,
            grahakAakarshan = grahakAakarshan,
            grahakSamadhan = grahakSamadhan,
            parasparSambandh = parasparSambandh,
            annapurna = annapurna,
            vanshVriddhi = vanshVriddhi,

            // Lecher Antenna
            lecher176 = lecher176,
            lecher33 = lecher33,
            lecher42 = lecher42,
            lecher66 = lecher66,
            lecher80 = lecher80,
            lecher86 = lecher86,
            lecher180 = lecher180,
            lecher130 = lecher130,

            // Additional Notes
            notes = notes
        )
    }
}

@Composable
fun rememberFormState(): FormState {
    return remember { FormState() }
} 