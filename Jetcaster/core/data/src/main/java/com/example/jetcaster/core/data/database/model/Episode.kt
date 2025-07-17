package com.example.jetcaster.core.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.Duration
import java.time.OffsetDateTime

@Entity(
    tableName = "episodes",
    indices = [
        Index("uri", unique = true),
        Index("podcast_uri"),
    ],
    foreignKeys = [
        ForeignKey(
            entity = Podcast::class,
            parentColumns = ["uri"],
            childColumns = ["podcast_uri"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
@TypeConverters(ListOfStringConverter::class)
data class Episode(
    @PrimaryKey @ColumnInfo(name = "uri") val uri: String,
    @ColumnInfo(name = "podcast_uri") val podcastUri: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "subtitle") val subtitle: String? = null,
    @ColumnInfo(name = "summary") val summary: String? = null,
    @ColumnInfo(name = "author") val author: String? = null,
    @ColumnInfo(name = "published") val published: OffsetDateTime,
    @ColumnInfo(name = "duration") val duration: Duration? = null,
    @ColumnInfo(name = "media_urls") val mediaUrls: List<String>,
)

class ListOfStringConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}
