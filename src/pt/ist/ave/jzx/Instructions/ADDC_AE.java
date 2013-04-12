package pt.ist.ave.jzx.Instructions;

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
