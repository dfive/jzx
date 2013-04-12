package pt.ist.ave.jzx.Instructions;


public class INC_B extends Instruction {

	public INC_B(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_b8(_cpu.inc8(_cpu.getM_b8()));
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
		return 4;
	}

}
