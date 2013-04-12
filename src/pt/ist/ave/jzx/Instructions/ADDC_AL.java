package pt.ist.ave.jzx.Instructions;

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
