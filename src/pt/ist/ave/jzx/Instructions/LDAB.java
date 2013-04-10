package pt.ist.ave.jzx.Instructions;

public class LDAB extends Instruction {

	public LDAB(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.getM_b8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
