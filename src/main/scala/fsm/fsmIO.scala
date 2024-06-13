package scala.fsm

import chisel3._
import chisel3.util._

class PS2KeyboardIO extends Bundle {
    val ps2_clk = Input(Bool())
    val ps2_data = Input(Bool())
    val key_code = Output(UInt(8.W))
    val key_count = Output(UInt(8.W))
    val key_down = Output(Bool())
}