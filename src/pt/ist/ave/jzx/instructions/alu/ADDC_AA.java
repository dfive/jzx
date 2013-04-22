package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADDC_AA extends Instruction {

	public ADDC_AA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//add_a(m_a8);
		_cpu.adc_a(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		//m_tstates += 4;
		return 4;
	}

}
