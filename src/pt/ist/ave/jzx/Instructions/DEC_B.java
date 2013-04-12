package pt.ist.ave.jzx.Instructions;

public class DEC_B extends Instruction {

	public DEC_B(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_b8(_cpu.dec8(_cpu.getM_b8()));
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 4;
	}

}
