package com.example.jetcaster.tv.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.ButtonScale
import androidx.tv.material3.Icon
import androidx.tv.material3.Text

@Composable
internal fun ButtonWithIcon(
    label: String,
    @DrawableRes iconId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    scale: ButtonScale = ButtonDefaults.scale(),
) {
    Button(onClick = onClick, modifier = modifier, scale = scale) {
        Icon(
            painterResource(id = iconId),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = label)
    }
}
