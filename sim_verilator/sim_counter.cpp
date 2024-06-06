#include "verilated.h"
#include "verilated_vcd_c.h"
#include "../build/obj_dir/Vcountertop.h"

// static TOP_NAME dut;

VerilatedContext* contextp = NULL;
VerilatedVcdC* tfp = NULL;

static Vcountertop* top;

void step_and_dump_wave(){
  top->eval();
  contextp->timeInc(1);
  tfp->dump(contextp->time());
}
void sim_init(){
  contextp = new VerilatedContext;
  tfp = new VerilatedVcdC;
  top = new Vcountertop;
  contextp->traceEverOn(true);
  top->trace(tfp, 0);
  tfp->open("dump.vcd");
}


void sim_cycle() {
        for (int i = 0; i < 200000000; ++i) {  // 模拟 1000 个时钟周期
            if (i % 10 == 1) {  // 每 10 个时间单位翻转时钟
                top->clock = !top->clock;
            }

            if (i == 25001000) {  // 复位信号持续一段时间
                top->io_reset = 0;
            } else if (i == 25005000) {
                top->io_reset = 1;
            }

            if (i == 25005100) {  // 启动信号
                top->io_start = 1;
            }

            if (i == 25006100) {  // 暂停信号开启
                top->io_pause = 1;
            } else if (i == 25007100) {  // 暂停信号关闭
                top->io_pause = 0;
            }


            step_and_dump_wave();  // 每个时间单位评估和记录波形
    }

}


void sim_exit(){
  step_and_dump_wave();
  tfp->close();
  delete top;
  delete tfp;
  delete contextp;
}

int main() {

  sim_init();

  sim_cycle();
  
  sim_exit();
}