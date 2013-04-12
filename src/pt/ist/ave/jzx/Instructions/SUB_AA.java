package pt.ist.ave.jzx.Instructions;

public class SUB_AA extends Instruction {

	public SUB_AA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sub_a(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
