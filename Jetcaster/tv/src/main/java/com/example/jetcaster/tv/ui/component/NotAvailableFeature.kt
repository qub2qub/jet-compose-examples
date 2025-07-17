package com.example.jetcaster.tv.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.Text
import com.example.jetcaster.tv.R

@Composable
internal fun NotAvailableFeature(
    modifier: Modifier = Modifier,
    message: String = stringResource(id = R.string.message_not_available_feature),
) {
    Text(message, modifier = modifier)
}
