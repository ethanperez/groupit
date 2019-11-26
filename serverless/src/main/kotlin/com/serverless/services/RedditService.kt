package com.serverless.services

import net.dean.jraw.RedditClient
import net.dean.jraw.http.OkHttpNetworkAdapter
import net.dean.jraw.http.UserAgent
import net.dean.jraw.oauth.Credentials
import net.dean.jraw.oauth.OAuthHelper
import java.util.UUID

class RedditService {
    var client : RedditClient

    companion object {
        private val userAgent = UserAgent("bot", "com.groupit.mailer", "0.1", "aguy")
    }

    init {
        val credentials = Credentials.userless(System.getenv("REDDIT_CLIENT_ID"), System.getenv("REDDIT_CLIENT_SECRET"), UUID.randomUUID())
        client = OAuthHelper.automatic(OkHttpNetworkAdapter(userAgent), credentials)
    }
}