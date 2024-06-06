package mux2_1

import chisel3._
import chisel3.util._
import chisel3.stage._

class Mux21IOs extends Bundle {
    val a: UInt = Input(UInt(1.W))
    val b: UInt = Input(UInt(1.W))
    val s: UInt = Input(UInt(1.W))

    val y: UInt = Output(UInt(1.W))    
}


class mux21 extends Module {
    val io : Mux21IOs = IO(new Mux21IOs)

    io.y := (~io.s & io.a) | (io.s & io.b)
}


// object mux2_1Main extends App {
//     emitVerilog(new mux21, Array("--target-dir", "generated"))
// }