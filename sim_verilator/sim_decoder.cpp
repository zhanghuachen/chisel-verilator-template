#include "verilated.h"
#include "verilated_vcd_c.h"
#include "../build/obj_dir/Vtop.h"

static TOP_NAME dut;

VerilatedContext* contextp = NULL;
VerilatedVcdC* tfp = NULL;

static Vtop* top;

void step_and_dump_wave(){
  top->eval();
  contextp->timeInc(1);
  tfp->dump(contextp->time());
}
void sim_init(){
  contextp = new VerilatedContext;
  tfp = new VerilatedVcdC;
  top = new Vtop;
  contextp->traceEverOn(true);
  top->trace(tfp, 0);
  tfp->open("dump.vcd");
}

void sim_cycle() {
    // Assign values to io_sw and simulate
    top->io_sw = 0b00000001; step_and_dump_wave();
    top->io_sw = 0b00000010; step_and_dump_wave();
    top->io_sw = 0b00000100; step_and_dump_wave();
    top->io_sw = 0b00001000; step_and_dump_wave();
    top->io_sw = 0b00010000; step_and_dump_wave();
    top->io_sw = 0b00100000; step_and_dump_wave();
    top->io_sw = 0b01000000; step_and_dump_wave();
    top->io_sw = 0b10000000; step_and_dump_wave();
}



void sim_exit(){
  step_and_dump_wave();
  tfp->close();
}

int main() {

  sim_init();

  sim_cycle();
  
  sim_exit();
}