package common.checker

import common.checker.actions.CustomSessionCheckAction
import io.gatling.core.session.Expression

trait CustomCheckDsl {
  /**
   * custom checker
   *
   * @param name checker name, will display on gating test reports
   * @return ActionBuilder
   */
  def customSessionChecker(name: Expression[String]): CustomSessionCheckAction = CustomSessionCheckAction(name)
}
