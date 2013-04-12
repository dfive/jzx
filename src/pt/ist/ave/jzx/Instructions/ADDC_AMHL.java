package pt.ist.ave.jzx.Instructions;

public class ADDC_AMHL extends Instruction {

	public ADDC_AMHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work8 = _cpu.getM_memory().read8(_cpu.hl16());
		_cpu.adc_a(work8);
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
