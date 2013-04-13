package pt.ist.ave.jzx.Instructions;

public class CP_MHL extends Instruction {

	public CP_MHL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		int work8 = _cpu.getM_memory().read8(_cpu.hl16());
		_cpu.cmp_a(work8);
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
