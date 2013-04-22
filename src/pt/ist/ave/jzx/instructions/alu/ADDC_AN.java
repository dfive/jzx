package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADDC_AN extends Instruction {

	public ADDC_AN(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		//add_a(m_a8);
		int work8 = _cpu.getM_memory().read8(_cpu.inc16pc());
		_cpu.adc_a(work8);
	}

	@Override
	public int incTstates() {
		//m_tstates += 4;
		return 7;
	}

}
