package scala.counter

import chisel3._
import chisel3.util._

class divide extends Module {
    val io : divideIO = IO(new divideIO)


    val maxCounter = (50000000 /2 - 1).U
    val DivideCounter = RegInit(0.U(26.W))

    val clkReg = RegInit(false.B)


    DivideCounter := DivideCounter + 1.U

    when(DivideCounter === maxCounter) {
        DivideCounter := 0.U
        clkReg := ~ clkReg
    }

    io.clk_out := clkReg
}