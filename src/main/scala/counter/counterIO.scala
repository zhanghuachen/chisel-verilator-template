package scala.counter

import chisel3._
import chisel3.util._


class CounterIO extends Bundle {
    val clk_in = Input(Bool())
    val start : Bool = Input(Bool())
    val pause = Input(Bool())
    val reset = Input(Bool())
    val counter_out = Output(UInt(8.W))
}

class divideIO extends Bundle {
    val clk_out = Output(Bool())
}

class TopIO extends Bundle {
    val start = Input(Bool())
    val pause = Input(Bool())
    val reset = Input(Bool())

    val seg7_0 = Output(UInt(7.W))
    val seg7_1 = Output(UInt(7.W))
}