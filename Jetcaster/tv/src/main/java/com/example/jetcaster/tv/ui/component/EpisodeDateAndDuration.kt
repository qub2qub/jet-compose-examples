package com.example.jetcaster.tv.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.jetcaster.tv.R
import java.time.Duration
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val MediumDateFormatter by lazy {
    DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
}

@Composable
internal fun EpisodeDataAndDuration(
    offsetDateTime: OffsetDateTime,
    duration: Duration,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
) {
    Text(
        text = stringResource(
            R.string.episode_date_duration,
            MediumDateFormatter.format(offsetDateTime),
            duration.toMinutes().toInt(),
        ),
        style = style,
        modifier = modifier,
    )
}
