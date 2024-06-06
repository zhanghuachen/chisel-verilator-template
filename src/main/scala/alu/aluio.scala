package scala.alu

import chisel3._
import chisel3.util._


class aluIO extends Bundle {
    val op : UInt = Input(UInt(3.W))

    val a : SInt = Input(SInt(4.W))
    val b : SInt = Input(SInt(4.W))

    val out : SInt = Output(SInt(4.W))

    val overflow :UInt = Output(UInt(1.W))
    val zero : UInt = Output(UInt(1.W))
    val carry_out : UInt = Output(UInt(1.W))

}