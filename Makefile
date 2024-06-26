TOPNAME = top
NXDC_FILES = constr/top.nxdc
INC_PATH ?=

VERILATOR = verilator
VERILATOR_CFLAGS += -MMD --build -cc  --trace \
				-O3 --x-assign fast --x-initial fast --noassert

BUILD_DIR = ./build
OBJ_DIR = $(BUILD_DIR)/obj_dir
BIN = $(BUILD_DIR)/$(TOPNAME)

default: $(BIN)

$(shell mkdir -p $(BUILD_DIR))

# constraint file
SRC_AUTO_BIND = $(abspath $(BUILD_DIR)/auto_bind.cpp)
$(SRC_AUTO_BIND): $(NXDC_FILES)
	python3 $(NVBOARD_HOME)/scripts/auto_pin_bind.py $^ $@


# Mill command to generate Verilog
MILL = mill
MILL_TARGET = top
generate:
	$(MILL) $(MILL_TARGET)



# project source
VSRCS = $(shell find $(abspath ./generated) -name "*.v" -or -name "*.sv") 
# CSRCS = $(shell find $(abspath ./sim_verilator) -name "*.c" -or -name "*.cc" -or -name "*.cpp")
CSRCS = $(shell find $(abspath ./sim_verilator) -name "sim_top.cpp")
CSRCS += $(SRC_AUTO_BIND)


# Add SDL2 include and library paths
SDL2_INCLUDE_PATH = /usr/local/include/SDL2
SDL2_LIBRARY_PATH = /usr/local/lib
 
CFLAGS += -I$(SDL2_INCLUDE_PATH)
LDFLAGS += -L$(SDL2_LIBRARY_PATH) -lSDL2 -lSDL2_ttf

# rules for NVBoard
include $(NVBOARD_HOME)/scripts/nvboard.mk



# rules for verilator
INCFLAGS = $(addprefix -I, $(INC_PATH))
CXXFLAGS += $(INCFLAGS) -DTOP_NAME="\"V$(TOPNAME)\"" $(CFLAGS)
sim:$(BIN)
$(BIN): $(VSRCS) $(CSRCS) $(NVBOARD_ARCHIVE) 
	@rm -rf $(OBJ_DIR)
	$(VERILATOR) $(VERILATOR_CFLAGS) \
		--top-module $(TOPNAME)  $(VSRCS) $(CSRCS) $(NVBOARD_ARCHIVE) \
		$(addprefix -CFLAGS , $(CXXFLAGS)) $(addprefix -LDFLAGS , $(LDFLAGS)) \
		--Mdir $(OBJ_DIR) --exe -o $(abspath $(BIN))

all: default

run: $(BIN)
	@$^

clean:
	mill clean
	rm -rf $(BUILD_DIR) generated out dump.vcd

.PHONY: default all clean run
