package pt.ist.ave.jzx.Instructions.ld;

import pt.ist.ave.jzx.Instructions.Instruction;

public class LD_AC extends Instruction {

	public LD_AC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_a8 = m_c8;
		_cpu.setM_a8(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
//		m_tstates += 4;
		return 4;
	}

}
