package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class DEC_A extends Instruction {

	public DEC_A(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//m_a8 = dec8(m_a8);
		_cpu.setM_a8(_cpu.dec8(_cpu.getM_a8()));
		
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
