package com.example.sayisalloto.extension

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class LimitedLengthVisualTransformation(private val maxLength: Int) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // Metni filtrele ve maksimum karakter sınırını uygula
        val newText = text.text.take(maxLength)
        return TransformedText(AnnotatedString(newText), offsetMapping = OffsetMapping.Identity)
    }
}
