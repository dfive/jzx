package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADD_HLBC extends Instruction {

	public ADD_HLBC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		add_hl(bc16());
		_cpu.add_hl(_cpu.bc16());
	}

	@Override
	public int incTstates() {
//		m_tstates += 11;
		return 11;
	}

}
