package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_BN extends Instruction {

	public LD_BN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_b8(_cpu.getM_memory().read8(_cpu.inc16pc()));
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
