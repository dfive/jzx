package pt.ist.ave.jzx.Instructions;

public class OR_L extends Instruction {

	public OR_L(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.or_a(_cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
