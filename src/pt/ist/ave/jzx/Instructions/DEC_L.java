package pt.ist.ave.jzx.Instructions;

public class DEC_L extends Instruction {

	public DEC_L(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
		_cpu.setM_l8(_cpu.dec8(_cpu.getM_l8()));
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
