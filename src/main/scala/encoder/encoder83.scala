package scala.encoder

import chisel3._
import chisel3.util._



class encoder83 extends Module {
    val io : encoder83IO = IO(new encoder83IO)

    io.out := 0.U

    when(io.en){    
        for(i <- 7 until 0 by -1) {
            when(io.a(i) === 1.U) { io.out := i.U}
        }
    }

}
