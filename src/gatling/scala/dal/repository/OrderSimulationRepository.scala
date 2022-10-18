package dal.repository


import dal.`do`.OrderDO


object OrderSimulationRepository {

  def findById(orderId: Int): OrderDO = {
    simulateQueryDataFromDatabase(orderId)
  }

  private def simulateQueryDataFromDatabase(orderId: Int): OrderDO = {
    val orderDo = new OrderDO
    orderDo.id = orderId
    orderDo.orderNo = "00000000" + orderId
    orderDo.orderState = "INACTIVE"
    orderDo
  }
}