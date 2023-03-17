package com.example.quickgpt.classes.models.gptmodels

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import java.time.Instant
import java.util.*


@Serializable
data class GptModel(
    val id: String,
    @JsonNames("object")
    val object_field: String,
    private val created: Long,
    @JsonNames("owned_by")
    val ownedBy: String,
    val permission: List<Permission>,
    val root: String,
    val parent: String?,
){
    val createdDate : Date
        get() = Date.from(Instant.ofEpochSecond(created))

    val is_text :Boolean =
        (  """^text""".toRegex().containsMatchIn(id)   )

    val is_gpt3 :Boolean =
        when(id){
    "davinci"  ->  true;
        "ada"  ->  true;
    "babbage"  ->  true;
      "curie"  ->  true;
        else   ->  false;
    }.or(id.run {
            contains("text-[a-z]{1,10}-001".toRegex())
    })

    val is_gpt3_5 :Boolean = id.run {(
                contains("davinci-00[2-3]$".toRegex()) ||
                        contains("turbo".toRegex())
    )}



    val is_similarity :Boolean =
        (  """similarity""".toRegex().containsMatchIn(id)   )
    val is_search :Boolean =
        (  """search""".toRegex().containsMatchIn(id)   )
    val is_sansNumeric :Boolean = id.run {
            contains("[0-9]".toRegex())
    }

    val is_filtered :Boolean =
        (
            id.contains(":") ||
            id.contains("instruct") ||
            is_search ||
            is_similarity
        )


}


@Serializable
data class Permission(
    val id: String,
    @JsonNames("object")
    val object_field: String,
    val created: Long,
    @JsonNames("allow_create_engine")
    val allowCreateEngine: Boolean,
    @JsonNames("allow_sampling")
    val allowSampling: Boolean,
    @JsonNames("allow_logprobs")
    val allowLogprobs: Boolean,
    @JsonNames("allow_search_indices")
    val allowSearchIndices: Boolean,
    @JsonNames("allow_view")
    val allowView: Boolean,
    @JsonNames("allow_fine_tuning")
    val allowFineTuning: Boolean,
    val organization: String,
    val group: String?,
    @JsonNames("is_blocking")
    val isBlocking: Boolean,
)