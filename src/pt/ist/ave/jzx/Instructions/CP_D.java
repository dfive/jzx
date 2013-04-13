package pt.ist.ave.jzx.Instructions;

public class CP_D extends Instruction {

	public CP_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.cmp_a(_cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
