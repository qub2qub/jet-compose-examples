package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@Composable
internal fun TwoColumn(
    first: (@Composable RowScope.() -> Unit),
    second: (@Composable RowScope.() -> Unit),
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal =
        Arrangement.spacedBy(JetcasterAppDefaults.gap.twoColumn),
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        modifier = modifier,
    ) {
        first()
        second()
    }
}
