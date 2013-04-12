package pt.ist.ave.jzx.Instructions;

public class SUBC_AMHL extends Instruction {

	public SUBC_AMHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work8 = _cpu.getM_memory().read8(_cpu.hl16());
		_cpu.sbc_a(work8);
	}

	@Override
	public int incTstates() {
		return 7;
	}

}
