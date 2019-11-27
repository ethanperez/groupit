package com.serverless.models

data class TemplateData(
        val subject: String,
        val title: String,
        val subheading: String,
        val data: List<PostGroup>
)

data class Post(
        val title: String,
        val postHref: String,
        val commentHref: String,
        val commentCount: Int
)

data class PostGroup(
        val source: PostSource,
        val name: String,
        val posts: List<Post>
)

enum class PostSource {
    Reddit,
}