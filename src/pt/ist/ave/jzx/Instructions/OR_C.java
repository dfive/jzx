package pt.ist.ave.jzx.Instructions;

public class OR_C extends Instruction {

	public OR_C(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.or_a(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
