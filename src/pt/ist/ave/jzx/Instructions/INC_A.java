package pt.ist.ave.jzx.Instructions;

public class INC_A extends Instruction {

	public INC_A(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_a8(_cpu.inc8(_cpu.getM_a8()));
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
