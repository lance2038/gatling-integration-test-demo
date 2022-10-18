package business.behaviors.database

import common.checker.Predef.customSessionChecker
import dal.repository.OrderSimulationRepository
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder


object CheckOrderBehavior {

  val checkOrder: ChainBuilder =
    exec(customSessionChecker("Check Order Status & Extra OrderNo")
      .executeCheck(session => {
        var submitOrderSuccess = false
        val orderDO = OrderSimulationRepository.findById(session("orderId").as[Int])

        // store the 'orderNo' to the session, but can not get it below
        session.set("orderNo", orderDO.orderNo)
        if ("ACTIVE" == orderDO.orderState) {
          submitOrderSuccess = true
        }
        // get orderNo from session, but can not find 'orderNo' in session
        println("-------------------------session-orderNo in custom checker:" + session("orderNo").as[Any])
        submitOrderSuccess
      })
      .errorMsg("Check Order Status Failed"))
      .exitHereIfFailed
      .exec(session => {
        // get orderNo from session, but can not find 'orderNo' in session
        println("-------------------------session-orderNo out custom checker:" + session("orderNo").as[Any])
        session
      })


}
