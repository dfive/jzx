package pt.ist.ave.jzx.Instructions;

public class INC_D extends Instruction {

	public INC_D(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		_cpu.setM_d8(_cpu.inc8(_cpu.getM_d8()));
	}

	@Override
	public int incTstates() {
		return 4;
	}

}
