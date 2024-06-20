package scala.fsm

import chisel3._
import chisel3.util._


class KeyBoardLookupTableIO extends Bundle {
    val sanCode = Input(UInt(8.W))
    val key = Output(UInt(8.W))
    val valid = Output(UInt(1.W))
}

class KeyBoardLookupTable extends Module {
    val io : KeyBoardLookupTableIO = IO(new KeyBoardLookupTableIO)

      // 默认输出
    io.key := 0.U
    io.valid := false.B


    // 定义查找表
    val lookupTable = Seq(
        // 第一行
        "h76".U -> "h1B".U, // ESC
        // 功能键
        "h05".U -> "h70".U, // F1
        "h06".U -> "h71".U, // F2
        "h04".U -> "h72".U, // F3
        "h0C".U -> "h73".U, // F4
        "h03".U -> "h74".U, // F5
        "h0B".U -> "h75".U, // F6
        "h83".U -> "h76".U, // F7
        "h0A".U -> "h77".U, // F8
        "h01".U -> "h78".U, // F9
        "h09".U -> "h79".U, // F10
        "h78".U -> "h7A".U, // F11
        "h07".U -> "h7B".U, // F12
        // 数字键
        "h16".U -> "h31".U, // 1
        "h1E".U -> "h32".U, // 2
        "h26".U -> "h33".U, // 3
        "h25".U -> "h34".U, // 4
        "h2E".U -> "h35".U, // 5
        "h36".U -> "h36".U, // 6
        "h3D".U -> "h37".U, // 7
        "h3E".U -> "h38".U, // 8
        "h46".U -> "h39".U, // 9
        "h45".U -> "h30".U, // 0
        // 字母键
        "h1C".U -> "h41".U, // A
        "h32".U -> "h42".U, // B
        "h21".U -> "h43".U, // C
        "h23".U -> "h44".U, // D
        "h24".U -> "h45".U, // E
        "h2B".U -> "h46".U, // F
        "h34".U -> "h47".U, // G
        "h33".U -> "h48".U, // H
        "h43".U -> "h49".U, // I
        "h3B".U -> "h4A".U, // J
        "h42".U -> "h4B".U, // K
        "h4B".U -> "h4C".U, // L
        "h3A".U -> "h4D".U, // M
        "h31".U -> "h4E".U, // N
        "h44".U -> "h4F".U, // O
        "h4D".U -> "h50".U, // P
        "h15".U -> "h51".U, // Q
        "h2D".U -> "h52".U, // R
        "h1B".U -> "h53".U, // S
        "h2C".U -> "h54".U, // T
        "h3C".U -> "h55".U, // U
        "h2A".U -> "h56".U, // V
        "h1D".U -> "h57".U, // W
        "h22".U -> "h58".U, // X
        "h35".U -> "h59".U, // Y
        "h1A".U -> "h5A".U, // Z
        // 控制键
        "h0D".U -> "h09".U, // TAB
        "h29".U -> "h20".U, // SPACE
        "h5A".U -> "h0D".U, // ENTER
        "h66".U -> "h08".U, // BACKSPACE
        "h12".U -> "h00".U, // SHIFT (left)
        "h59".U -> "h00".U, // SHIFT (right)
        "h14".U -> "h00".U, // CTRL (left)
        "hE014".U -> "h00".U, // CTRL (right)
        "h11".U -> "h00".U, // ALT (left)
        "hE011".U -> "h00".U  // ALT (right)
            // 其他按键根据需求补充
    )

  val scanCodes = VecInit(lookupTable.map(_._1))
  val keys = VecInit(lookupTable.map(_._2))


  // 查找表逻辑
  for (i <- lookupTable.indices) {
    when(io.sanCode === scanCodes(i)) {
      io.key := keys(i)
      io.valid := true.B
    }
  }

}