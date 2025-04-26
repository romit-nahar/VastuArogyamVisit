package com.example.vastuarogyamvisit.utils

/**
 * Constants for Vastu-specific dropdown options
 */
object VastuDropdownConstants {
    val VASTUBHOOMIDOSH = listOf(
        "NA",
        "वास्तुभूमिदोष आहे",
        "वास्तुभूमिदोष नाही"
    )

    val RAHNYAS_YOGYA = listOf(
        "NA",
        "उपाय करून काही काळ राहण्यासाठी योग्य",
        "व्यावसायिक कामासाठी योग्य",
        "उपाय करून राहण्यासाठी योग्य",
        "राहण्यासाठी योग्य",
        "राहण्यासाठी योग्य नाही, त्वरित सोडावे व भाड्याने घ्यावे",
        "राहण्यासाठी योग्य नाही, त्वरित सोडावे व विकावे"
    )

    val SHALYA_DOSH = listOf(
        "NA",
        "इतर नकारात्मक वस्तू",
        "लाकूड",
        "हाडे",
        "लोखंड",
        "कपाल (खोपडी)",
        "केस",
        "कोंडसा",
        "राख",
        "भुसा",
        "पीव्हीसी/प्लास्टिक",
        "शल्य दोष नाही"
    )

    val ENTITY = listOf(
        "NA",
        "इतर",
        "समूह",
        "पूर्वीच्या मालकाशी संबंधित",
        "हानिकारक नाही",
        "जिन",
        "पुण्यात्मा",
        "अडकलेली आत्मा",
        "तांत्रिक कारणांमुळे आलेली",
        "बाहेरील व्यक्ती",
        "घरातील व्यक्ती",
        "Entity नाही"
    )

    val VASTUTIL_SPANDANE = listOf(
        "Positive",
        "Negative"
    )

    val MAANVIYA_DOSH = listOf(
        "NA",
        "प्रकाश विकिरणाचा त्रास",
        "मर्म स्थानाचा वेध",
        "शरीरातील अवशेष दुखावले आहेत",
        "घरात अश्लील शब्दांचा उच्चार",
        "प्रवेशद्वार चुकीचे",
        "रंगसंगती चुकीची",
        "स्त्रीवर अत्याचार",
        "अशुभ कर्माचा त्रास",
        "तांत्रिक वस्तू घरामध्ये",
        "घरात नको असलेल्या वस्तू",
        "मुहूर्ताचा वेध चुकीचा",
        "आतील रचना चुकीची",
        "बांधकाम साहित्य दोष",
        "आय-व्‍यय अप्रामाणिक",
        "अयोग्य पूजा पद्धती",
        "अस्वच्छता"
    )

    val AMAANVIYA_DOSH = listOf(
        "NA",
        "चेटूक",
        "पूर्वजन्म शाप",
        "कुलदेवताची अवकृपा",
        "हाका, चित्कार",
        "पितृ पीडा",
        "देवता बंधन",
        "भूमी शापित आहे",
        "इतर",
        "दृष्ट लागणे",
        "सर्प शाप",
        "करणी",
        "तांत्रिकाचा त्रास",
        "अतृप्त आत्मा",
        "वास्तुपुरुष/मृत पुरुष पीडा",
        "क्षुद्रदेवतांचा त्रास",
        "भूत बाधा",
        "पिशाच्च पीडा",
        "मांत्रिकाचा त्रास",
        "षट्कर्म",
        "अन्य काळी विद्या त्रास"
    )

    val GHARATIL_VASTAVYA_SPANDANE = listOf(
        "NA",
        "धनवृद्धी",
        "मानसिक शांतता",
        "प्रसन्नता",
        "आरोग्यदायी",
        "मैत्रीपूर्ण",
        "कोर्ट-कचेरी",
        "भरभराट",
        "कलह",
        "वंशवृद्धी",
        "धनहानी",
        "शोककारक",
        "मृत्यूकारक",
        "अनारोग्य",
        "द्वेषपूर्ण",
        "भीती/तनाव",
        "नुकसान",
        "कुलनाश"
    )

    val JASTIT_JAST_KAMIT_KAMI_DOSH = listOf(
        "NA",
        "उत्तर",
        "भूमी खाली",
        "उत्तर ईशान्य",
        "ईशान्य",
        "पूर्व ईशान्य",
        "पूर्व",
        "पूर्व आग्नेय",
        "आग्नेय",
        "दक्षिण आग्नेय",
        "ब्रह्मस्थान",
        "दक्षिण",
        "वरच्या दिशेला",
        "दक्षिण नेत्रत्य",
        "नेत्रत्य",
        "पश्चिम नेत्रत्य",
        "पश्चिम",
        "पश्चिम वायव्य",
        "वायव्य",
        "उत्तर वायव्य",
        "वास्तूच्या बाहेर"
    )

    val YES_NO = listOf(
        "YES",
        "NO")

    val PERCENTAGE = listOf(
        "0%", "5%", "10%", "15%", "20%",
        "25%", "30%", "35%", "40%", "45%",
        "50%", "55%", "60%", "65%", "70%",
        "75%", "80%", "85%", "90%", "95%",
        "100%"
    )

    val GS = listOf(
        "-ve GS",
        "10", "20", "30", "40", "50",
        "60", "70", "80", "90", "100",
        "+ve GS"
    )


}

/**
 * Constants for general dropdown options
 */
object DropdownConstants {
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

/**
 * Constants for font resources
 */
object FontConstants {
    const val DEVANAGARI_FONT_PATH = "NotoSansDevanagari-Regular.ttf"
}