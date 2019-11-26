package com.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.google.gson.Gson
import com.serverless.models.Post
import com.serverless.models.TemplateData
import com.serverless.services.RedditService
import net.dean.jraw.models.SubredditSort
import net.dean.jraw.models.TimePeriod
import org.apache.logging.log4j.LogManager
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.ses.SesClient
import software.amazon.awssdk.services.ses.model.Destination
import software.amazon.awssdk.services.ses.model.SendTemplatedEmailRequest

class Handler:RequestHandler<Map<String, Any>, ApiGatewayResponse> {
  override fun handleRequest(input:Map<String, Any>, context:Context):ApiGatewayResponse {

    val redditClient = RedditService().client
    val a = redditClient.subreddit("nba").posts().sorting(SubredditSort.TOP).timePeriod(TimePeriod.DAY).limit(5).build()
    val posts = a.next().map { Post(it.title, it.url) }.toList()

    val templateDataString = Gson().toJson(TemplateData("Here's an email", posts))

    val client = SesClient.builder().apply {
      region(Region.US_EAST_1)
    }.build()

    val templateEmailRequest = SendTemplatedEmailRequest.builder()
            .destination(Destination.builder().toAddresses("ethan@ethanperez.com").build())
            .source("GroupIt <letter@groupit.email>")
            .template("GroupItBasic")
            .templateData(templateDataString)
            .build()

    client.sendTemplatedEmail(templateEmailRequest)


    return ApiGatewayResponse.build {
      statusCode = 200
    }
  }

  companion object {
    private val LOG = LogManager.getLogger(Handler::class.java)
  }
}
