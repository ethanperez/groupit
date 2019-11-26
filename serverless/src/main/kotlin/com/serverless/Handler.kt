package com.serverless

import com.amazonaws.regions.Regions
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder
import com.amazonaws.services.simpleemail.model.*
import org.apache.logging.log4j.LogManager

class Handler:RequestHandler<Map<String, Any>, ApiGatewayResponse> {
  override fun handleRequest(input:Map<String, Any>, context:Context):ApiGatewayResponse {
    LOG.info("received: " + input.keys.toString())

    val client = AmazonSimpleEmailServiceClientBuilder.standard().apply {
      region = Regions.US_EAST_1.toString()
    }.build()

    val request = SendEmailRequest().apply {
      destination = Destination().withToAddresses("test@ethanperez.com")
      message = Message().apply {
        body = Body().apply {
          text = Content().apply {
            data = "this is some text"
          }
        }
        subject = Content().apply {
          data = "Welcome Message"
        }
      }
      source = "test@t4k.pw"
    }

    client.sendEmail(request)

    return ApiGatewayResponse.build {
      statusCode = 200
      objectBody = HelloResponse("Go Serverless v1.x! Your Kotlin function executed successfully!", input)
      headers = mapOf("X-Powered-By" to "AWS Lambda & serverless")
    }
  }

  companion object {
    private val LOG = LogManager.getLogger(Handler::class.java)
  }
}
