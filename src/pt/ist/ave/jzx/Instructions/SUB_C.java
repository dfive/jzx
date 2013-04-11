package pt.ist.ave.jzx.Instructions;

public class SUB_C extends Instruction {

	public SUB_C(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sub_a(_cpu.getM_c8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
