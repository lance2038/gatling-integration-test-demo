package business.behaviors.database

import common.checker.Predef.customSessionChecker
import dal.repository.OrderSimulationRepository
import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder


object CheckOrderBehavior {

  val checkOrder: ChainBuilder =
    exec()
      .exec(session => {
        session.set("aiiiiii", "dddddddddddddddd")
        session
      })
      .exec(customSessionChecker("Check Order Status & Extra OrderNo")
        .executeCheck(session => {
          var submitOrderSuccess = false
          val orderDO = OrderSimulationRepository.findById(session("orderId").as[Int])

          // store the 'orderNo' to the session, but can not get it below
          if ("ACTIVE" == orderDO.orderState) {
            submitOrderSuccess = true
          }
          // get orderNo from session, but can not find 'orderNo' in session
          submitOrderSuccess
        })
        .errorMsg("Check Order Status Failed"))
      .exitHereIfFailed
      .exec(session => {
        // get orderNo from session, but can not find 'orderNo' in session
        session
      })


}
