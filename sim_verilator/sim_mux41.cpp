#include "verilated.h"
#include "verilated_vcd_c.h"
#include "../build/obj_dir/Vmux41.h"

#include <nvboard.h>

static TOP_NAME dut;

void nvboard_bind_all_pins(TOP_NAME* top);

VerilatedContext* contextp = NULL;
VerilatedVcdC* tfp = NULL;

static Vmux41* top;

void step_and_dump_wave(){
  top->eval();
  contextp->timeInc(1);
  tfp->dump(contextp->time());
}
void sim_init(){

  nvboard_init();

  contextp = new VerilatedContext;
  tfp = new VerilatedVcdC;
  top = new Vmux41;
  nvboard_bind_all_pins(top);
  contextp->traceEverOn(true);
  top->trace(tfp, 0);
  tfp->open("dump.vcd");
}


void sim_cycle() {
        nvboard_update(); 
        step_and_dump_wave();
        printf("top->io_y=%d top->io_x0=%d top->io_x1=%d top->io_x2=%d  top->io_x3=%d top->io_f=%d\n",top->io_y, top->io_x0,top->io_x1,top->io_x2,top->io_x3, top->io_f);
        // top->io_y=0b00;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
        //                  top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();
        // top->io_y=0b01;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
        //                  top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();
        // top->io_y=0b10;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
        //                  top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();
        // top->io_y=0b11;  top->io_x0=0b11; top->io_x1=0b01; top->io_x2=0b00;  top->io_x3=0b10; step_and_dump_wave();
        //                  top->io_x0=0b10; top->io_x1=0b01; top->io_x2=0b01;  top->io_x3=0b00; step_and_dump_wave();

}


void sim_exit(){
  step_and_dump_wave();
  tfp->close();
}

int main() {

  sim_init();
while (1)
{
  /* code */
  sim_cycle();
}

  
  sim_exit();
}