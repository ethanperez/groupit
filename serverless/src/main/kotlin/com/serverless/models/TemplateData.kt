package com.serverless.models

data class TemplateData(
        val subject: String,
        val posts: List<Post>
)

data class Post(
        val title: String,
        val href: String
)