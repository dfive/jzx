package pt.ist.ave.jzx.instructions.alu;

import pt.ist.ave.jzx.instructions.Instruction;

public class ADDC_AC extends Instruction {

	public ADDC_AC(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.adc_a(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
