package scala.alu

import chisel3._
import chisel3.util._

class alu extends Module {
    val io : aluIO = IO(new aluIO)

    val sum = Wire(SInt(5.W))
    val diff = Wire(SInt(5.W))

    sum  := io.a +& io.b
    diff := io.a -& io.b

    
    io.carry_out := 0.U
    io.overflow := 0.U
    io.zero := 0.U(1.W)


    //io.out := 0.S(4.W)



    // switch (io.op) {
    //     is ("b000".U) {
    //         io.out := sum(3, 0).asSInt
    //         io.carry_out := sum(4).asUInt
    //     }
    //     is ("b001".U) {
    //         io.out := sum(3, 0).asSInt
    //         io.carry_out := diff(4).asUInt
    //     }
    //     is ("b010".U) {
    //         io.out := ~ io.a
    //     }

    //     is ("b011".U) {
    //         io.out := io.a & io.b
    //     }

    //     is("b100".U) {
    //         io.out := io.a | io.b
    //     }
    //     is("b101".U) {
    //         io.out := io.a ^ io.b
    //     }
    //     is("b110".U) {
    //         when (io.a  < io.b) { io.out := 1.S(4.W)}
    //         .otherwise {io.out := 0.S(4.W)}
    //     }
    //     is("b111".U) {
    //         when (io.a  === io.b) { io.out := 1.S(4.W)}
    //         .otherwise {io.out := 0.S(4.W)}
    //     }

    // }


    io.out := MuxCase(0.S(4.W), Seq(
        (io.op === "b000".U) -> sum(3, 0).asSInt,
        (io.op === "b001".U) -> diff(3, 0).asSInt,
        (io.op === "b010".U) -> (~io.a).asSInt,
        (io.op === "b011".U) -> (io.a & io.b),
        (io.op === "b100".U) -> (io.a | io.b),
        (io.op === "b101".U) -> (io.a ^ io.b),
        (io.op === "b110".U) -> Mux(io.a < io.b, 1.S(4.W), 0.S(4.W)),
        (io.op === "b111".U) -> Mux(io.a === io.b, 1.S(4.W), 0.S(4.W))
    ))


    io.carry_out := Mux(io.op === "b000".U, sum(4).asUInt,
                    Mux(io.op === "b001".U, diff(4).asUInt, 0.U))

}



    // object alu extends App {
    //  emitVerilog(new alu, Array("--target-dir", "generated"))
    // }