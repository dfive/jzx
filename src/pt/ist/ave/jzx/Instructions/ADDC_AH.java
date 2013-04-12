package pt.ist.ave.jzx.Instructions;

public class ADDC_AH extends Instruction {

	public ADDC_AH(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.adc_a(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
