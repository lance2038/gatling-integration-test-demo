package business.apis

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder


object SimulateSubmitOrderAPI {

  val requestName = "Simulate Submit Order - #{orderId}"

  val send: HttpRequestBuilder = http(requestName)
    .get("/computers")
    .check(status.is(200))
}
