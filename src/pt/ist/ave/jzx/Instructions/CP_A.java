package pt.ist.ave.jzx.Instructions;

public class CP_A extends Instruction {

	public CP_A(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.cmp_a(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
