package scala.shift

import chisel3._
import chisel3.util._

class ShiftIO extends Bundle {
    val num = Input(UInt(8.W))

    val res = Output(UInt(8.W))
}

class Shift extends Module {
    val io : ShiftIO = IO(new ShiftIO)

    val highBit = RegInit(0.U(1.W))


    highBit := io.num(3) ^ io.num(2) ^ io.num(1) ^ io.num(0)

    io.res := Cat(highBit, io.num(7,1))
}

object countertop extends App {
     emitVerilog(new Shift, Array("--target-dir", "generated"))
}