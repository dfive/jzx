package pt.ist.ave.jzx.Instructions;

public class CP_E extends Instruction {

	public CP_E(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.cmp_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
