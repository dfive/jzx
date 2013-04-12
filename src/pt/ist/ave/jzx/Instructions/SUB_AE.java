package pt.ist.ave.jzx.Instructions;

public class SUB_AE extends Instruction {

	public SUB_AE(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sub_a(_cpu.getM_e8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
