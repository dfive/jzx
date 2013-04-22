package pt.ist.ave.jzx.instructions.ld;

import pt.ist.ave.jzx.instructions.Instruction;

public class LD_LA extends Instruction {

	public LD_LA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_l8 = m_a8;
		_cpu.setM_l8(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
//		m_tstates += 4;
		return 4;
	}

}
