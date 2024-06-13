package mux4_1

import chisel3._
import chisel3.util._

class Mux41IOs extends Bundle {
    val x0 : UInt = Input(UInt(2.W))
    val x1 : UInt = Input(UInt(2.W))
    val x2 : UInt = Input(UInt(2.W))
    val x3 : UInt = Input(UInt(2.W))

    val y : UInt = Input(UInt(2.W))

    val f :UInt = Output(UInt(2.W))
}

class mux41 extends Module {
    val io : Mux41IOs = IO(new Mux41IOs)

    io.f := 0.U

    switch(io.y) {
        is (0.U) {io.f := io.x0}
        is (1.U) {io.f := io.x1}
        is (2.U) {io.f := io.x2}
        is (3.U) {io.f := io.x3}
    }
}

object mux4_1 extends App {
    emitVerilog(new mux41, Array("--target-dir", "generated"))
}
