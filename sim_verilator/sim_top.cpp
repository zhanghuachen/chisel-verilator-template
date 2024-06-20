#include "verilated.h"
#include "verilated_vcd_c.h"
#include "../build/obj_dir/Vtop.h"

#include <nvboard.h>

// static TOP_NAME dut;

void nvboard_bind_all_pins(TOP_NAME* top);

VerilatedContext* contextp = NULL;
VerilatedVcdC* tfp = NULL;

static Vtop* top;

void step_and_dump_wave(){
  
  top->clock = 1;
  top->eval();
  top->clock = 0;
  top->eval();

 // contextp->timeInc(1);
 // tfp->dump(contextp->time());
}
void sim_init(){
 // tfp = new VerilatedVcdC;
  top = new Vtop;
 // contextp = new VerilatedContext;
 // Verilated::traceEverOn(true);

 // top->trace(tfp, 0);
 // tfp->open("dump.vcd");
  nvboard_init();
  top -> reset = 1;
  step_and_dump_wave();
  step_and_dump_wave();
  top -> reset = 0;
  nvboard_bind_all_pins(top);
}


void sim_cycle() {
        nvboard_update(); 
        int pre[6] = {0};

        step_and_dump_wave();
        
        // if(pre[0] != top->io_out_0 || pre[1] != top->io_out_1 || pre[2] != top->io_out_2 || pre[3] != top->io_out_3 || pre[4] != top->io_out_4 || pre[5] != top->io_out_5) {
        //     printf("top->io_out_0=%d top->io_out_1=%d top->io_out_2=%d top->io_out_3=%d  top->io_out_4=%d top->io_out_5=%d \n",top->io_out_0, top->io_out_1,top->io_out_2,top->io_out_3,top->io_out_4, top->io_out_5);
        //     pre[0] = top->io_out_0 ; 
        //     pre[1] = top->io_out_1 ; 
        //     pre[2] = top->io_out_2 ; 
        //     pre[3] = top->io_out_3 ; 
        //     pre[4] = top->io_out_4 ;
        //     pre[5] = top->io_out_5 ;
        // }
        

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