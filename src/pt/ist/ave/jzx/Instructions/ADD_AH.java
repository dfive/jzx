package pt.ist.ave.jzx.Instructions;

public class ADD_AH extends Instruction {

	public ADD_AH(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.add_a(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
