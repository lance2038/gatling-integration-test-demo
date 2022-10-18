package business.simulations

import business.behaviors.database.CheckOrderBehavior
import business.behaviors.order.SubmitOrderBehavior
import io.gatling.core.Predef._
import io.gatling.core.controller.inject.open.OpenInjectionStep
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

class SubmitOrderSimulation extends Simulation {

  val httpConf: HttpProtocolBuilder =
    http.baseUrl("https://computer-database.gatling.io/")


  val scenarioBuilder: ScenarioBuilder =
    scenario("Submit Order")
      .exec(_.set("orderId", 1))
      .exec(SubmitOrderBehavior.execute)
      .exec(CheckOrderBehavior.checkOrder)
      .exitHereIfFailed
      .exec(_.set("orderId", 2))
      .exec(SubmitOrderBehavior.execute)
  setUp(scenarioBuilder.inject(onceUserInjector, rampUserInjector)).protocols(httpConf)


  def onceUserInjector: OpenInjectionStep = {
    atOnceUsers(1)
  }

  def rampUserInjector: OpenInjectionStep = {
    rampUsers(0) during (0 seconds)
  }
}
