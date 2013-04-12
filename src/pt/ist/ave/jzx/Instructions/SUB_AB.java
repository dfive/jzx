package pt.ist.ave.jzx.Instructions;

public class SUB_AB extends Instruction {

	public SUB_AB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sub_a(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
