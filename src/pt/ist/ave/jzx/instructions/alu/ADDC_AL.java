package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADDC_AL extends Instruction {

	public ADDC_AL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.adc_a(_cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
