package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class AND_A extends Instruction {

	public AND_A(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//and_a(m_a8);
		_cpu.and_a(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
