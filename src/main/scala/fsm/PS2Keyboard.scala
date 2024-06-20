// package scala.fsm

// import chisel3._
// import chisel3.util._

// class PS2KeyboardIO extends Bundle {
//     val ps2_clk = Input(Bool())
//     val ps2_data = Input(Bool())
//     val key_code = Output(UInt(8.W))
//     val key_count = Output(UInt(8.W))
//     val key_down = Output(Bool())
// }


// class PS2Keyboard extends Module {
//     val io : PS2KeyboardIO = IO(new PS2KeyboardIO)


//     val buffer = RegInit(0.U(10.W))
//     val count = RegInit(0.U(4.W))
//     val ps2_clk_sync = RegInit(0.U(3.W))
//     val keyup_flag = RegInit(false.B)
//     val key_down = RegInit(false.B)
//     val key_code = RegInit(0.U(8.W))
//     val key_count = RegInit(0.U(8.W))

//     //status
//     val idle :: receive :: process :: Nil = Enum(3)
//     val state = RegInit(idle)

//     ps2_clk_sync := Cat(ps2_clk_sync(1,0), io.ps2_clk)
//     val sampling = ps2_clk_sync(2) && !ps2_clk_sync(1)


//     switch(state) {
//         is(idle) {
//             when(sampling) {
//                 buffer := Cat(io.ps2_data, buffer(9,1))
//                 count := count + 1.U
//                 when (count === 9.U) {
//                     state := process
//                 } .otherwise {
//                     state := receive
//                 }
//             }
//         }

//         is(receive) {
//             when (sampling) {
//                 buffer := Cat(io.ps2_data, buffer(9,1))
//                 count := count + 1.U
//                 when (count === 9.U) {
//                     state := process
//                 } .otherwise {
//                     state := receive
//                 }
//             }
//         }

//         is (process) {
//             // 检查起始位、停止位和奇偶校验位是否正确
//             when (buffer(0) === 0.U && io.ps2_data && buffer(9,1).xorR) {
//                 key_code := buffer(8,1)
//                 // 检测是否为按键松开事件
//                 when (buffer(8,1) === 0XF0.U) {
//                     key_count := key_count + 1.U
//                     keyup_flag := true.B
//                 } .otherwise {
//                     when (keyup_flag) {
//                         key_down := false.B
//                         keyup_flag := false.B
//                     } .otherwise {
//                         key_down := true.B
//                     }
//                 }
//             }
//             count := 0.U
//             state := idle
//         }

//     }


//     io.key_code := key_code
//     io.key_count := key_count
//     io.key_down := key_down

// }

package scala.fsm

import chisel3._
import chisel3.util._

class PS2KeyboardIO extends Bundle {
  val ps2_clk = Input(Bool())
  val ps2_data = Input(Bool())
  val key_code = Output(UInt(8.W))
  val key_count = Output(UInt(8.W))
  val key_down = Output(Bool())
}

class PS2Keyboard extends Module {
  val io: PS2KeyboardIO = IO(new PS2KeyboardIO)

  val buffer = RegInit(0.U(11.W)) // 增加缓冲区宽度，以处理起始位和停止位
  val count = RegInit(0.U(4.W))
  val ps2_clk_sync = RegInit(0.U(3.W))
  val keyup_flag = RegInit(false.B)
  val key_down = RegInit(false.B)
  val key_code = RegInit(0.U(8.W))
  val key_count = RegInit(0.U(8.W))

  // 状态机定义
  val idle :: receive :: process :: Nil = Enum(3)
  val state = RegInit(idle)

  // 时钟同步
  ps2_clk_sync := Cat(ps2_clk_sync(1, 0), io.ps2_clk)
  val sampling = ps2_clk_sync(2) && !ps2_clk_sync(1)

  switch(state) {
    is(idle) {
      when(io.ps2_data === false.B) { // 检测起始位
        state := receive
      }
    }

    is(receive) {
      when(sampling) {
        buffer := Cat(io.ps2_data, buffer(10, 1))
        count := count + 1.U
        when(count === 10.U) {
          state := process
        }
      }
    }

    is(process) {
      // 检查起始位、停止位和奇偶校验位
      when(buffer(0) === 0.U && buffer(10) === 1.U && buffer(9, 1).xorR) {
        key_code := buffer(8, 1)
        when(buffer(8, 1) === "hF0".U) { // 检测断码
          keyup_flag := true.B
        }.otherwise {
          when(keyup_flag) {
            key_down := false.B
            keyup_flag := false.B
          }.otherwise {
            key_down := true.B
            key_count := key_count + 1.U
          }
        }
      }
      count := 0.U
      state := idle
    }
  }

  io.key_code := key_code
  io.key_count := key_count
  io.key_down := key_down
}
