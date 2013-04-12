package pt.ist.ave.jzx.Instructions;

public class SUB_AH extends Instruction {

	public SUB_AH(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.sub_a(_cpu.getM_h8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
