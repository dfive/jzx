package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class INC_H extends Instruction {

	public INC_H(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//m_h8 = inc8(m_h8);
		_cpu.setM_h8(_cpu.inc8(_cpu.getM_h8()));
	}

	
	@Override
	public int incTstates() {
		return 4;
	}

}
