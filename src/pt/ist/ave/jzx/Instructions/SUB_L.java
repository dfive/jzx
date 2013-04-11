package pt.ist.ave.jzx.Instructions;

public class SUB_L extends Instruction {

	public SUB_L(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sub_a(_cpu.getM_l8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
