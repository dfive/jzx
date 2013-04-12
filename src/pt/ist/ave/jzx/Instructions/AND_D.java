package pt.ist.ave.jzx.Instructions;

public class AND_D extends Instruction {

	public AND_D(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.and_a(_cpu.getM_d8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
