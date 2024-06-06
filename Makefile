TOPNAME = countertop

INC_PATH ?=

VERILATOR = verilator
VERILATOR_CFLAGS += -MMD --build -cc  --trace \
				-O3 --x-assign fast --x-initial fast --noassert

BUILD_DIR = ./build
OBJ_DIR = $(BUILD_DIR)/obj_dir
BIN = $(BUILD_DIR)/$(TOPNAME)

default: $(BIN)

$(shell mkdir -p $(BUILD_DIR))


generated/countertop.sv:
	mill countertop

# project source
VSRCS = $(shell find $(abspath ./generated) -name "*.v" -or -name "*.sv") 
# CSRCS = $(shell find $(abspath ./sim_verilator) -name "*.c" -or -name "*.cc" -or -name "*.cpp")
CSRCS = $(shell find $(abspath ./sim_verilator) -name "sim_counter.cpp")
CSRCS += $(SRC_AUTO_BIND)


# rules for verilator
INCFLAGS = $(addprefix -I, $(INC_PATH))
CXXFLAGS += $(INCFLAGS) -DTOP_NAME="\"V$(TOPNAME)\"" $(CFLAGS)

$(BIN): $(VSRCS) $(CSRCS) $(NVBOARD_ARCHIVE) generated/countertop.sv
	@rm -rf $(OBJ_DIR)
	$(VERILATOR) $(VERILATOR_CFLAGS) \
		--top-module $(TOPNAME)  $(VSRCS) $(CSRCS) \
		$(addprefix -CFLAGS , $(CXXFLAGS)) $(addprefix -LDFLAGS , $(LDFLAGS)) \
		--Mdir $(OBJ_DIR) --exe -o $(abspath $(BIN))

all: default

run: $(BIN)
	@$^

clean:
	mill clean
	rm -rf $(BUILD_DIR) generated out dump.vcd

.PHONY: default all clean run
