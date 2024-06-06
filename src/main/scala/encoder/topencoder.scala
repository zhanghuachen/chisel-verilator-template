

import encoder._


import chisel3._
import chisel3.util._

class topencoderIO extends Bundle {
    val sw = Input(UInt(8.W))

    val seg_out = Output(UInt(7.W))
    val led_out = Output(UInt(3.W))
}

class topencoder extends Module{
    val io : topencoderIO = IO(new topencoderIO)

    val encoder = Module(new encoder83)
    val bcd = Module(new bcd7seg)

    io.led_out := encoder.io.out

    encoder.io.a := io.sw
    encoder.io.en := 1.U


    bcd.io.b := Cat(0.U(1.W), encoder.io.out)
    io.seg_out := bcd.io.h
}

// object topencoder extends App {
//      emitVerilog(new topencoder, Array("--target-dir", "generated"))
// }
