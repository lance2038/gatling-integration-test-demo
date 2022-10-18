package common.checker

import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.session.{Expression, Session}
import io.gatling.core.structure.ScenarioContext

package object actions {


  case class CustomSessionCheckAction(requestName: Expression[String]) {
    // input a function with boolean result. if result == true means check success
    def executeCheck(func: Session => Boolean): CustomSessionCheckActionBuilder = CustomSessionCheckActionBuilder(requestName, func)
  }

  case class CustomSessionCheckActionBuilder(requestName: Expression[String],
                                             func: Session => Boolean,
                                             checkMsg: Option[String] = None) extends ActionBuilder {

    def errorMsg(msg: String): CustomSessionCheckActionBuilder = this.copy(checkMsg = Some(msg))

    override def build(ctx: ScenarioContext, next: Action): Action = {
      import ctx._
      val statsEngine = coreComponents.statsEngine
      val clock = coreComponents.clock
      CustomSessionCheckBaseAction(statsEngine, clock, requestName, func, checkMsg, next, ctx)
    }
  }
}
