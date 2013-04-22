package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class DEC_E extends Instruction {

	public DEC_E(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		m_e8 = dec8(m_e8);
		_cpu.setM_e8(_cpu.dec8(_cpu.getM_e8()));
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 4;
		return 4;
	}

}
