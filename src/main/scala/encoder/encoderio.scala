package scala.encoder

import chisel3._
import chisel3.util._

class encoder83IO extends Bundle {
    val a : UInt = Input(UInt(8.W))
    val en : Bool = Input(Bool())

    val out : UInt = Output(UInt(3.W))
}

class bcd7segIO extends Bundle {
    val b : UInt = Input(UInt(4.W))
    val h : UInt = Output(UInt(7.W))
}