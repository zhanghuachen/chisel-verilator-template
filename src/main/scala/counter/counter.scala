package scala.counter

import chisel3._
import chisel3.util._

class counter extends Module {
    val io : CounterIO = IO(new CounterIO)

    val count = RegInit(0.U(8.W))
    val running = RegInit(false.B)


    when(io.reset) {
        count := 0.U
        running := false.B
    } .elsewhen(io.pause) {
        running := false.B
    } .elsewhen(io.start) {
        running := true.B
    }

    when (running && io.clk_in) {
        count := Mux(count === 99.U , 0.U, count + 1.U )
    }

    io.counter_out := count

}