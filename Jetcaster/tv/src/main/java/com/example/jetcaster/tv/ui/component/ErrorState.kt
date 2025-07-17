package com.example.jetcaster.tv.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.tv.material3.Button
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.jetcaster.tv.R
import com.example.jetcaster.tv.ui.theme.JetcasterAppDefaults

@Composable
fun ErrorState(backToHome: () -> Unit, modifier: Modifier = Modifier, focusRequester: FocusRequester = remember { FocusRequester() }) {
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = stringResource(R.string.display_error_state),
                style = MaterialTheme.typography.displayMedium,
            )
            Button(
                onClick = backToHome,
                modifier
                    .padding(top = JetcasterAppDefaults.gap.podcastRow)
                    .focusRequester(focusRequester),
            ) {
                Text(text = stringResource(R.string.label_back_to_home))
            }
        }
    }
}
