package scala.fsm

import chisel3._
import chisel3.util._

class bcd7segIO extends Bundle {
    val beardkey :UInt = Input(UInt(8.W))
    val asciikey : UInt = Input(UInt(8.W))
    val keynumber : UInt = Input(UInt(8.W))
    val out = Output(Vec(8,UInt(8.W)))
}



class bcd7seg extends Module {
    val io = IO(new bcd7segIO)

    io.out := VecInit(Seq.fill(8)(0.U(8.W)))


    def segDisplay(value : UInt) : UInt = {
         MuxLookup(value, "b11111111".U(8.W))(Seq(
            0.U ->  "b00000011".U(8.W),  // 0
            1.U ->  "b10011111".U(8.W),  // 1
            2.U ->  "b00100101".U(8.W),  // 2
            3.U ->  "b00001101".U(8.W),  // 3
            4.U ->  "b10011001".U(8.W),  // 4
            5.U ->  "b01001001".U(8.W),  // 5
            6.U ->  "b01000001".U(8.W),  // 6
            7.U ->  "b00011111".U(8.W),  // 7
            8.U ->  "b00000001".U(8.W),  // 8
            9.U ->  "b00001001".U(8.W),  // 9
            "hA".U -> "b00010001".U(8.W),  // A
            "hB".U -> "b11000001".U(8.W),  // B
            "hC".U -> "b01100011".U(8.W),  // C
            "hD".U -> "b10000101".U(8.W),  // D
            "hE".U -> "b01100001".U(8.W),  // E
            "hF".U -> "b01110001".U(8.W),  // F
        ))
    }

    val highbeardkey = io.beardkey(7, 4)
    val lowbeardkey = io.beardkey(3, 0)

    val highasciikey = io.asciikey(7, 4)
    val lowasciikey = io.asciikey(3, 0)

    val highkeynumber = (io.keynumber / 10.U)(3, 0)
    val lowkeynumber = (io.keynumber % 10.U)(3, 0)


    io.out(0) := segDisplay(lowbeardkey)
    io.out(1) := segDisplay(highbeardkey)
    io.out(2) := segDisplay(lowasciikey)
    io.out(3) := segDisplay(highasciikey)
    io.out(4) := segDisplay(lowkeynumber)
    io.out(5) := segDisplay(highkeynumber)


}