package pt.ist.ave.jzx.Instructions;

public class LD_EA extends Instruction {

	public LD_EA(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_e8(_cpu.getM_a8());
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
