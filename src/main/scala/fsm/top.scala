package scala.fsm

import chisel3._
import chisel3.util._


class topIO extends Bundle {
    val ps2_clk = Input(Bool())
    val ps2_data = Input(Bool())
    val out = Output(Vec(8, UInt(8.W)))
}

class top extends Module {
    val io = IO(new topIO)

    io.out := VecInit(Seq.fill(8)(0.U(8.W)))

    val PS2Keyboardtop = Module(new PS2Keyboard)
    val KeyBoardLookupTabletop = Module(new KeyBoardLookupTable)
    val bcd7segtop = Module(new bcd7seg)


    PS2Keyboardtop.io.ps2_clk := io.ps2_clk
    PS2Keyboardtop.io.ps2_data := io.ps2_data
    

    KeyBoardLookupTabletop.io.sanCode := PS2Keyboardtop.io.key_code 

    bcd7segtop.io.beardkey := KeyBoardLookupTabletop.io.sanCode
    bcd7segtop.io.asciikey := KeyBoardLookupTabletop.io.key
    bcd7segtop.io.keynumber := PS2Keyboardtop.io.key_count

    io.out := bcd7segtop.io.out
    
}

object top extends App {
     emitVerilog(new top, Array("--target-dir", "generated"))
}