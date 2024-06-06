#include "verilated.h"
#include "verilated_vcd_c.h"
#include "../build/obj_dir/Vmux41.h"

static TOP_NAME dut;

VerilatedContext* contextp = NULL;
VerilatedVcdC* tfp = NULL;

static Vmux41* top;

void step_and_dump_wave(){
  top->eval();
  contextp->timeInc(1);
  tfp->dump(contextp->time());
}
void sim_init(){
  contextp = new VerilatedContext;
  tfp = new VerilatedVcdC;
  top = new Vmux41;
  contextp->traceEverOn(true);
  top->trace(tfp, 0);
  tfp->open("dump.vcd");
}


void sim_cycle() {
        top->io_y=0b00;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
                         top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();
        top->io_y=0b01;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
                         top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();
        top->io_y=0b10;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
                         top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();
        top->io_y=0b11;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
                         top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();

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