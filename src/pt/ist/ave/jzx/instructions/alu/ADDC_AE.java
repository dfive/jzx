package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADDC_AE extends Instruction {

	public ADDC_AE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.adc_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
