package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_DENN extends Instruction {

	public LD_DENN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.de16(_cpu.getM_memory().read16(_cpu.getM_pc16()));
		_cpu.setM_pc16(_cpu.incinc16(_cpu.getM_pc16()));
	}

	@Override
	public int incTstates() {
		return 10;
	}

}
