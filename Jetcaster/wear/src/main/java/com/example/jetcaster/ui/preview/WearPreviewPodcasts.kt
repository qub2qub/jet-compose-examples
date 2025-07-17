package com.example.jetcaster.ui.preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.jetcaster.core.domain.testing.PreviewPodcasts
import com.example.jetcaster.core.model.PodcastInfo

public class WearPreviewPodcasts : PreviewParameterProvider<PodcastInfo> {
    public override val values: Sequence<PodcastInfo>
        get() = PreviewPodcasts.asSequence()
}
