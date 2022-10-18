package business.behaviors.order

import business.apis.SimulateSubmitOrderAPI
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder


object SubmitOrderBehavior {

  val execute: ChainBuilder = {
    exec(SimulateSubmitOrderAPI.send)
  }

}
