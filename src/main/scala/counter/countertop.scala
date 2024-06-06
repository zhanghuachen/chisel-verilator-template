package scala.counter

import chisel3._
import chisel3.util._



class countertop extends Module {
    val io : TopIO = IO(new TopIO)

    val countertop = Module(new counter)
    val dividetop = Module(new divide)


    countertop.io.start := io.start
    countertop.io.pause := io.pause
    countertop.io.reset := io.reset

    countertop.io.clk_in := dividetop.io.clk_out
    

    // seg_out
    def BCDDecoder(value: UInt): UInt = {
        MuxLookup(value, "b1111111".U(7.W))(Seq(
            0.U -> "b0000001".U(7.W),  // 0
            1.U -> "b1001111".U(7.W),  // 1
            2.U -> "b0000010".U(7.W),  // 2
            3.U -> "b1000110".U(7.W),  // 3
            4.U -> "b0101100".U(7.W),  // 4
            5.U -> "b0100100".U(7.W),  // 5
            6.U -> "b0100000".U(7.W),  // 6
            7.U -> "b0001111".U(7.W),  // 7
            8.U -> "b0000000".U(7.W),  // 8
            9.U -> "b0000100".U(7.W)   // 9
        ))
    }

    val tens = countertop.io.counter_out / 10.U
    val ones = countertop.io.counter_out % 10.U

    io.seg7_0 := BCDDecoder(ones)
    io.seg7_1 := BCDDecoder(tens)

}

// object countertop extends App {
//      emitVerilog(new countertop, Array("--target-dir", "generated"))
// }