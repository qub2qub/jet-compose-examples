package com.example.jetcaster.designsystem.component

import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml

/**
 * A container for text that should be HTML formatted. This container will handle building the
 * annotated string from [text], and enable text selection if [text] has any selectable element.
 */
@Composable
fun HtmlTextContainer(text: String, content: @Composable (AnnotatedString) -> Unit) {
    val annotatedString = remember(key1 = text) {
        AnnotatedString.fromHtml(htmlString = text)
    }
    SelectionContainer {
        content(annotatedString)
    }
}
