package scala.encoder

import chisel3._
import chisel3.util._

class bcd7seg extends Module {
    val io : bcd7segIO = IO(new bcd7segIO)

    io.h := "b000_0001".U

    switch(io.b) {
        is (0.U) {io.h := "b000_0001".U}
        is (1.U) {io.h := "b100_1111".U}
        is (2.U) {io.h := "b000_0010".U}
        is (3.U) {io.h := "b100_0110".U}
        is (4.U) {io.h := "b010_1100".U}
        is (5.U) {io.h := "b010_0100".U}
        is (6.U) {io.h := "b010_0000".U}
        is (7.U) {io.h := "b000_1111".U}
        is (8.U) {io.h := "b000_0000".U}
        is (9.U) {io.h := "b000_0100".U}

    }
}