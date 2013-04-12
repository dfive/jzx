package pt.ist.ave.jzx.Instructions;

public class AND_E extends Instruction {

	public AND_E(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.and_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
