package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class SUB_AN extends Instruction {

	public SUB_AN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work8 = _cpu.getM_memory().read8(_cpu.inc16pc());
		_cpu.sub_a(work8);
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
