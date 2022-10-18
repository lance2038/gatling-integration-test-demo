package common.checker.actions

import io.gatling.commons.stats.{KO, OK, Status}
import io.gatling.commons.util.Clock
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.session.{Expression, Session}
import io.gatling.core.stats.StatsEngine
import io.gatling.core.structure.ScenarioContext
import io.gatling.core.util.NameGen

case class CustomSessionCheckBaseAction(statsEngine: StatsEngine,
                                        clock: Clock,
                                        requestName: Expression[String],
                                        func: Session => Boolean,
                                        checkMsg: Option[String],
                                        next: Action,
                                        ctx: ScenarioContext) extends ExitableAction with NameGen {

  override def name: String = genName("custom session checker")

  override def execute(session: Session): Unit = {
    val resolvedName: String = requestName(session).toOption.get
    val start = ctx.coreComponents.clock.nowMillis
    // execute query
    val execute: Boolean =
      try {
        func(session)
      } catch {
        case _: Throwable => false
      }
    //  check the result
    val status: Status = if (execute) OK else KO
    val end = ctx.coreComponents.clock.nowMillis
    // if check failed save the error message to log
    val message = if (status == KO) checkMsg else None
    ctx.coreComponents.statsEngine.logResponse(session.scenario, session.groups, resolvedName, start, end, status, None, message)
    if (status == KO) {
      next ! session.markAsFailed
    } else {
      next ! session.markAsSucceeded
    }
  }
}
