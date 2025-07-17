package com.example.jetcaster.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.jetcaster.core.domain.testing.PreviewPlayerEpisodes
import com.example.jetcaster.core.player.model.PlayerEpisode

public class WearPreviewEpisodes : PreviewParameterProvider<PlayerEpisode> {
    public override val values: Sequence<PlayerEpisode>
        get() = PreviewPlayerEpisodes.asSequence()
}
