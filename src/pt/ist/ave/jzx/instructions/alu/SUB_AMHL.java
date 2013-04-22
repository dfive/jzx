package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class SUB_AMHL extends Instruction {

	public SUB_AMHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work8 = _cpu.getM_memory().read8(_cpu.hl16());
		_cpu.sub_a(work8);
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
