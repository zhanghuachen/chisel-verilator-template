package mux2_1

import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class Mux21Tester extends AnyFlatSpec with ChiselScalatestTester {
  "Mux21" should "pass" in {
    test(new mux21) { dut =>
      // Set initial values and step the clock
      dut.io.a.poke(0.U)
      dut.io.b.poke(0.U)
      dut.io.s.poke(0.U)
      dut.clock.step(1)
      dut.io.y.expect(0.U)

      dut.io.b.poke(1.U)
      dut.clock.step(1)
      dut.io.y.expect(0.U)

      dut.io.a.poke(1.U)
      dut.io.b.poke(0.U)
      dut.clock.step(1)
      dut.io.y.expect(1.U)

      dut.io.b.poke(1.U)
      dut.clock.step(1)
      dut.io.y.expect(1.U)

      dut.io.s.poke(1.U)
      dut.io.a.poke(0.U)
      dut.io.b.poke(0.U)
      dut.clock.step(1)
      dut.io.y.expect(0.U)

      dut.io.b.poke(1.U)
      dut.clock.step(1)
      dut.io.y.expect(1.U)

      dut.io.a.poke(1.U)
      dut.io.b.poke(0.U)
      dut.clock.step(1)
      dut.io.y.expect(0.U)

      dut.io.b.poke(1.U)
      dut.clock.step(1)
      dut.io.y.expect(1.U)
    }
  }
}
