package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_AMNN extends Instruction {

	public LD_AMNN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		_cpu.setM_a8(_cpu.getM_memory().read8(_cpu.getM_memory().read16(_cpu.getM_pc16())));
		_cpu.setM_a8(_cpu.getM_memory().read8(readNextMemoryPosition16()));
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 13;
	}

}
