package com.nbs.moviedb.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nbs.moviedb.domain.models.DetailMovie
import com.nbs.moviedb.domain.models.Favorite
import com.nbs.moviedb.domain.models.toGenreString
import com.nbs.moviedb.presentation.utils.toYearDate

/**
 * Created by aydbtiko on 7/7/2021.
 *
 */
@Entity(tableName = "favorite")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long? = null,
    @ColumnInfo(name = "movie_id")
    val movieId: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "backdrop_url")
    val backdropUrl: String,
    @ColumnInfo(name = "year_date")
    val yearDate: String,
    @ColumnInfo(name = "genres")
    val genres: String
)

/**Mapper*/
fun FavoriteEntity.asDomainModel(): Favorite {
    return Favorite(
        id = this.id ?: -1,
        movieId = this.movieId,
        title = this.title,
        backdropUrl = this.backdropUrl,
        yearDate = this.yearDate,
        genres = this.genres
    )
}

fun List<FavoriteEntity>.asDomainModels(): List<Favorite> {
    return this.map { it.asDomainModel() }
}

fun Favorite.asEntity(): FavoriteEntity {
    return FavoriteEntity(
        movieId = this.movieId,
        title = this.title,
        backdropUrl = this.backdropUrl,
        yearDate = this.yearDate,
        genres = this.genres
    )
}

fun DetailMovie.asFavorite(): Favorite {
    return Favorite(
        id = -1,
        movieId = this.id,
        title = this.title,
        backdropUrl = this.backdropUrl,
        yearDate = this.date.toYearDate(),
        genres = this.genres.toGenreString()
    )
}
