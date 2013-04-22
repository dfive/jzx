package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_SPHL extends Instruction {

	public LD_SPHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_sp16(_cpu.hl16());
	}

	@Override
	public int incTstates() {
		return 6;
	}

}
