package com.example.top100itunessongs.data.model

import com.google.gson.annotations.SerializedName

/**
 * General response
 */
data class SongsResponse(
    @SerializedName("feed")
    val feed: FeedData
)

/**
 * Songs response data
 */
data class FeedData(
    @SerializedName("entry")
    val songsResponse: List<SongItemResponse>
)

/**
 * Class containing general song item info
 */
data class SongItemResponse(
    @SerializedName("id")
    val id: SongItemId,
    @SerializedName("im:name")
    val title: SongItemTitle,
    @SerializedName("im:artist")
    val artist: SongItemArtist,
    @SerializedName("im:image")
    val imagesList: List<SongItemImage>,
    @SerializedName("im:price")
    val price: SongItemPrice,
    @SerializedName("category")
    val genre: SongItemCategory,
    @SerializedName("link")
    val shareLink: SongItemLink,
)

/**
 * Class containing item id data
 */
data class SongItemId(
    @SerializedName("label")
    val label: String,
    @SerializedName("attributes")
    val attributes: IDAttributes
)

/**
 * Class containing item id
 */
data class IDAttributes(
    @SerializedName("im:id")
    val idValue: String
)

/**
 * Class containing item title data
 */
data class SongItemTitle(
    @SerializedName("label")
    val titleValue: String
)

/**
 * Class containing item artist
 */
data class SongItemArtist(
    @SerializedName("label")
    val artistValue: String
)

/**
 * Class containing image url data
 */
data class SongItemImage(
    @SerializedName("label")
    val imageUrl: String
)

/**
 * Class containing item price info
 */
data class SongItemPrice(
    @SerializedName("label")
    val priceValue: String
)

/**
 * Class containing song link data
 */
data class SongItemLink(
    @SerializedName("attributes")
    val attributes: LinkAttributes
)

data class LinkAttributes(
    @SerializedName("href")
    val linkUrl: String
)

/**
 * Class containing song item category
 */
data class SongItemCategory (
    @SerializedName("attributes")
    val attributes: CategoryAttributes
)

/**
 * Class containing song item attributes
 */
data class CategoryAttributes (
    @SerializedName("term")
    val genreValue: String,
)

fun List<SongItemResponse>.convertToUiModel(): List<SongUiModel> {
    return this.map { response ->
        SongUiModel(
            id = response.id.attributes.idValue,
            title = response.title.titleValue,
            artist = response.artist.artistValue,
            price = response.price.priceValue,
            imageUrl = response.imagesList.last().imageUrl,
            genre = response.genre.attributes.genreValue,
            shareUrl = response.shareLink.attributes.linkUrl
        )
    }
}

