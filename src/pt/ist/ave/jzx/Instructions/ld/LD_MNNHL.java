package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_MNNHL extends Instruction {

	public LD_MNNHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.getM_memory().write16(_cpu.getM_memory().read16(_cpu.getM_pc16()), _cpu.hl16());
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 16;
	}

}
