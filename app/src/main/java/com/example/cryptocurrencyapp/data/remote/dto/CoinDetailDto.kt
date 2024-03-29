package com.example.cryptocurrencyapp.data.remote.dto

import com.example.cryptocurrencyapp.domain.model.CoinDetail
import com.google.gson.annotations.SerializedName

data class CoinDetailDto(
    val description: String,
    val development_status: String,
    val first_data_at: String,
    val hardware_wallet: Boolean,
    val hash_algorithm: String,
    val id: String,
    val is_active: Boolean,
    val is_new: Boolean,
    val last_data_at: String,
    val links: Links,
    val links_extended: List<LinksExtended>,
    val logo: String,
    val message: String,
    val name: String,
    val open_source: Boolean,
    val org_structure: String,
    val proof_type: String,
    val rank: Int,
    val started_at: String,
    val symbol: String,
    val tags: List<Tag>,
    @SerializedName("team")
    val teamMember: List<TeamMember>,
    val type: String,
    val whitepaper: Whitepaper
)

fun CoinDetailDto.toCoinDetail() : CoinDetail{
    return CoinDetail(
        coinId = id ?: "",
        name=name ?: "",
        description = description ?: "",
        symbol=symbol ?: "",
        rank=rank ?: 0,
        isActive = is_active ?: false,
        tags = tags.map { it.name } ?: emptyList<String>(),
        team = teamMember ?: emptyList()
    )
}