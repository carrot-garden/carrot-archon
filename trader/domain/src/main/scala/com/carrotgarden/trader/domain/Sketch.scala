package com.carrotgarden.trader.domain

class Order {
  class Status
  class Type
}
class OrderRequest
class OrderResponse

class Inst

class ForexInst(base: Currency, quote: Currency)

class Currency

class Price
class Size

class Money(price: Price, currency: Currency)

class Entry(money: Money, size: Size)

class Calendar
class Time

class Tick(inst: ForexInst, time: Time, bid: Price, ask: Price)

class Event
class EventData
class EventNews

class System

class Strategy
