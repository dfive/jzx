package pt.ist.ave.jzx.Instructions;

public class AND_B extends Instruction {

	public AND_B(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.and_a(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
